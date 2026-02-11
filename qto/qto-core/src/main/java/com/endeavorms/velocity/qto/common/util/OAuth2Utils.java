package com.endeavorms.velocity.qto.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author mmeehan
 * @since 1.0.0
 */
public final class OAuth2Utils {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2Utils.class);

    /** Jackson Object Mapper. */
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Private constructor.
     */
    private OAuth2Utils() {
    }

    /**
     * Refresh an access token.
     * @param oAuth2Token an OAuth2Token containing all info needed to refresh the access token.
     * @return an OAuth2Token with new access and refresh tokens.
     * @throws Exception if something breaks.
     */
    public static OAuth2TokenHolder refreshAccessToken(final OAuth2TokenHolder oAuth2Token) throws Exception {
        return refreshAccessToken(oAuth2Token, true);
    }

    /**
     * Refresh an access token.
     * @param tokenHolder an OAuth2Token containing all info needed to refresh the access token.
     * @param forceRefresh true to always refresh the access token, false to only refresh if access token has expired.
     * @return an OAuth2Token with new access and refresh tokens.
     * @throws Exception if something breaks.
     */
    public static OAuth2TokenHolder refreshAccessToken(final OAuth2TokenHolder tokenHolder,
                                                       final boolean forceRefresh) throws Exception {
        return refreshAccessToken(tokenHolder, forceRefresh, 0L);
    }


    /**
     * Refresh an access token.
     * @param tokenHolder an OAuth2Token containing all info needed to refresh the access token.
     * @param forceRefresh true to always refresh the access token, false to only refresh if access token has expired.
     * @param cutOffInSeconds the number of seconds prior to the current time to compare to token's expiresAt date.
     * @return an OAuth2Token with new access and refresh tokens.
     * @throws Exception if something breaks.
     */
    public static OAuth2TokenHolder refreshAccessToken(final OAuth2TokenHolder tokenHolder,
                                                       final boolean forceRefresh,
                                                       final Long cutOffInSeconds) throws Exception {

        if (!forceRefresh) {
            if (!accessTokenExpired(tokenHolder.getAccessToken(), cutOffInSeconds)) {
                LOGGER.debug("access token is not expired");
                return tokenHolder;
            } else {
                LOGGER.debug("access token is expired");
            }
        }

        LOGGER.debug("Refreshing access token");

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("refresh_token", tokenHolder.getRefreshToken()));
        params.add(new BasicNameValuePair("grant_type", "refresh_token"));
        params.add(new BasicNameValuePair("client_id", tokenHolder.getClientId()));
        params.add(new BasicNameValuePair("client_secret", tokenHolder.getClientSecret()));

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);

        Header[] headers = new Header[]{};

        String responseString = RestUtils.executePost(tokenHolder.getTokenUri(), formEntity, headers);

        LOGGER.trace("responseString = {}", responseString);

        String accessToken = OBJECT_MAPPER.readTree(responseString).get("access_token").asText();
        String refreshToken = OBJECT_MAPPER.readTree(responseString).get("refresh_token").asText();
        //Integer expiresIn = OBJECT_MAPPER.readTree(responseString).get("expires_in").asInt();
        //String tokenType =  OBJECT_MAPPER.readTree(responseString).get("token_type").asText();

        tokenHolder.setAccessToken(accessToken);
        tokenHolder.setRefreshToken(refreshToken);
        //tokenHolder.setExpiresIn(expiresIn);

        LOGGER.trace("accessToken = {}", accessToken);

        return tokenHolder;

    }


    /**
     * Determine whether the access token is expired.
     * @param accessTokenString a string containing access token JWT
     * @param cutOffInSeconds the number of seconds prior to the current time to compare to token's expiresAt date.
     * @return true if expired, false if not.
     */
    public static boolean accessTokenExpired(final String accessTokenString, final Long cutOffInSeconds) {
        DecodedJWT accessToken = JWT.decode(accessTokenString);

        Date accessTokenExpiresAt = accessToken.getExpiresAt();
        LocalDateTime accessTokenExpiresAtDateTime
                = accessTokenExpiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Set the time to require a fresh to be cutOffInSeconds prior to access token expiration date-time.
        Duration d = Duration.ofSeconds(cutOffInSeconds);
        LocalDateTime effectiveExpirationDateTime =
                LocalDateTime.from(d.subtractFrom(accessTokenExpiresAtDateTime));

        LOGGER.trace("accessToken.expiresAt = {}", accessToken.getExpiresAt().toString());
        LOGGER.trace("accessTokenExpiresAtDateTime = {}", accessTokenExpiresAtDateTime);
        LOGGER.trace("effectiveExpirationDateTime = {}", effectiveExpirationDateTime);

        return effectiveExpirationDateTime.isBefore(LocalDateTime.now());
    }

    /**
     * Obtain tokens using an authoriztion code.
     * @param tokenUri the uri for obtaining tokens.
     * @param authorizationCode the authorization code.
     * @param clientId the clientId.
     * @param clientSecret the client secret.
     * @param redirectUri the redirect uri. Must be same value used to obtain auth code.
     * @return an OAuth2TokenHolder populated with access and refresh tokens.
     * @throws Exception if an error occurs.
     */
    public static OAuth2TokenHolder obtainTokensUsingAuthorizationCode(final String tokenUri,
                                                                       final String authorizationCode,
                                                                       final String clientId,
                                                                       final String clientSecret,
                                                                       final String redirectUri) throws Exception {

        OAuth2TokenHolder tokenHolder = new OAuth2TokenHolder();
        tokenHolder.setTokenUri(tokenUri);
        tokenHolder.setClientId(clientId);
        tokenHolder.setClientSecret(clientSecret);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", authorizationCode));
        params.add(new BasicNameValuePair("redirect_uri", redirectUri));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        //params.add(new BasicNameValuePair("scope", scope));
        //params.add(new BasicNameValuePair("code_verifier", generateCodeVerifier()));

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);

        LOGGER.debug("tokenUri = {}", tokenUri);

        Header[] headers = new Header[]{};

        String responseString = RestUtils.executePost(tokenUri, formEntity, headers);

        LOGGER.trace("responseString = {}", responseString);

        String accessToken = OBJECT_MAPPER.readTree(responseString).get("access_token").asText();
        String refreshToken = OBJECT_MAPPER.readTree(responseString).get("refresh_token").asText();

        tokenHolder.setAccessToken(accessToken);
        tokenHolder.setRefreshToken(refreshToken);

        LOGGER.debug("accessToken = {}", accessToken);

        return tokenHolder;
    }

    /**
     * Obtain tokens client credentials grant type.
     * @param tokenUri the uri for obtaining tokens.
     * @param clientId the clientId.
     * @param clientSecret the client secret.
     * @return an OAuth2TokenHolder populated with access and refresh tokens.
     * @throws Exception if an error occurs.
     */
    public static OAuth2TokenHolder obtainTokensUsingClientCredentials(final String tokenUri,
                                                                       final String clientId,
                                                                       final String clientSecret) throws Exception {
        OAuth2TokenHolder tokenHolder = new OAuth2TokenHolder();
        tokenHolder.setTokenUri(tokenUri);
        tokenHolder.setClientId(clientId);
        tokenHolder.setClientSecret(clientSecret);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, StandardCharsets.UTF_8);

        LOGGER.debug("tokenUri = {}", tokenUri);

        Header[] headers = new Header[]{};

        String responseString = RestUtils.executePost(tokenUri, formEntity, headers);

        LOGGER.trace("responseString = {}", responseString);

        String accessToken = OBJECT_MAPPER.readTree(responseString).get("access_token").asText();
        // may not be present, will be null if not.
        JsonNode refreshTokenNode = OBJECT_MAPPER.readTree(responseString).get("refresh_token");
        String refreshToken = refreshTokenNode == null ? null : refreshTokenNode.asText();

        tokenHolder.setAccessToken(accessToken);
        tokenHolder.setRefreshToken(refreshToken);

        LOGGER.debug("accessToken = {}", accessToken);

        return tokenHolder;
    }

    /**
     * Generate a code verifier value. todo: make random.
     * @return a String.
     */
    private static String generateCodeVerifier() {
        return "0123456789012345678901234567890123456789012";
    }

}
