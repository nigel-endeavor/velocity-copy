package com.endeavorms.velocity.qto.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validates JWT bearer tokens and sets Spring Security context.
 * Replaces Shiro-based JWT auth for WebSocket and other programmatic auth.
 */
public final class JwtAuthenticationHelper {

    private JwtAuthenticationHelper() {
    }

    /**
     * Validates the bearer token and sets the SecurityContext.
     * Caller should clear the context when done if needed (e.g. after request scope).
     */
    public static void authenticateAndSetContext(String bearerToken) throws Exception {
        Jws<Claims> claims = validateAccessToken(bearerToken);
        List<GrantedAuthority> authorities = extractAuthorities(claims);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(claims, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @SuppressWarnings("unchecked")
    private static List<GrantedAuthority> extractAuthorities(Jws<Claims> claims) {
        List<String> roles = (List<String>) ((DefaultClaims) claims.getBody()).get("roles");
        if (roles == null) {
            return new ArrayList<>();
        }
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private static Jws<Claims> validateAccessToken(String accessToken) throws Exception {
        String aadClientId = System.getProperty("aadClientId");
        String aadTenantId = System.getProperty("aadTenantId");
        String authority = "https://login.microsoftonline.com/" + aadTenantId;
        String audience = "api://" + aadClientId;
        String issuer = "https://sts.windows.net/" + aadTenantId + "/";

        try {
            SigningKeyResolver resolver = new SigningKeyResolver(authority);
            return Jwts.parser()
                    .keyLocator(header -> resolver.resolveSigningKey((io.jsonwebtoken.JwsHeader) header, (Claims) null))
                    .requireAudience(audience)
                    .requireIssuer(issuer)
                    .build()
                    .parseSignedClaims(accessToken);
        } catch (SignatureException ex) {
            throw new JwtValidationException("Jwt validation failed: invalid signature", ex);
        } catch (ExpiredJwtException ex) {
            throw new JwtValidationException("Jwt validation failed: access token is expired", ex);
        } catch (MissingClaimException ex) {
            throw new JwtValidationException("Jwt validation failed: missing required claim", ex);
        } catch (IncorrectClaimException ex) {
            throw new JwtValidationException("Jwt validation failed: required claim has incorrect value", ex);
        }
    }
}
