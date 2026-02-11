package com.endeavorms.velocity.qto.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SigningKeyResolver extends SigningKeyResolverAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SigningKeyResolver.class);

    private final AADKeySet keySet;

    Map<JsonWebKey, PublicKey> publicKeyMap = new HashMap<>();

    SigningKeyResolver(String authority) throws Exception {
        keySet = getSigningKeys(authority);
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        String tokenKeyId = jwsHeader.getKeyId();
        for(JsonWebKey key: keySet.getKeys()){
            if(key.getKid().equalsIgnoreCase(tokenKeyId)){
                return getPublicKey(key);
            }
        }

        throw new JwtValidationException("Signature validation failed: Could not find a key with matching kid");
    }

    private AADKeySet getSigningKeys(String authority) throws Exception {
        OpenIdConnectConfiguration openIdConfig = getOpenIdConfiguration(authority);
        return getKeysFromJwkUri(openIdConfig.getJwksUri());
    }

    private OpenIdConnectConfiguration getOpenIdConfiguration(String authority) throws Exception {
        String openIdConnectDiscoveryEndpoint = authority + "/.well-known/openid-configuration";
        LOGGER.trace("Retrieving OpenID Config from {}", openIdConnectDiscoveryEndpoint);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(openIdConnectDiscoveryEndpoint))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseJson =  response.body();
        LOGGER.trace("OpenID Config retrieved");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseJson, OpenIdConnectConfiguration.class);
    }

    private AADKeySet getKeysFromJwkUri(String jwksUri) throws Exception {
        LOGGER.trace("Retrieving JWKS from {}", jwksUri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(jwksUri))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseJson =  response.body();
        LOGGER.trace("Retrieved JWKS");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseJson, AADKeySet.class);
    }

    public PublicKey getPublicKey(JsonWebKey key) {
        if (!publicKeyMap.containsKey(key)) {
            publicKeyMap.put(key, generatePublicKey(key));
        }
        return publicKeyMap.get(key);
    }

    private PublicKey generatePublicKey(JsonWebKey key) {
        LOGGER.trace("Generating JWK public key");
        try {
            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(key.getN()));
            BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(key.getE()));

            RSAPublicKeySpec publicSpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = factory.generatePublic(publicSpec);
            LOGGER.trace("JSK public key generated: {}", publicKey);
            return publicKey;
        } catch(Exception e){
            throw new JwtValidationException("Key generation failed", e);
        }
    }
}
