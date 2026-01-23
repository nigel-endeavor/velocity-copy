package com.vertek.corporate.qto.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.SignatureException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import java.util.List;

public class AADAuthorizingRealm extends AuthorizingRealm {

    private SigningKeyResolver signingKeyResolver;

    private String authority;
    private String audience;
    private String issuer;

    public AADAuthorizingRealm() {
        this.setAuthenticationTokenClass(BearerToken.class);
        this.setCredentialsMatcher(new AllowAllCredentialsMatcher());
    }

    protected void onInit() {
        String aadClientId = System.getProperty("aadClientId");
        String aadTenantId = System.getProperty("aadTenantId");
        this.setAuthority("https://login.microsoftonline.com/" + aadTenantId);
        this.setAudience("api://" + aadClientId);
        this.setIssuer("https://sts.windows.net/" + aadTenantId + "/");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> roles = (List<String>) ((DefaultClaims) ((Jws) principalCollection.getPrimaryPrincipal()).getBody()).get("roles");
        if (roles != null) {
            info.addStringPermissions(roles);
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken) throws AuthenticationException {
        BearerToken bearerToken = (BearerToken) authenticationToken;
        try {
            Jws<Claims> claims = validateAccessToken(bearerToken.getToken());
            PrincipalCollection principals = new SimplePrincipalCollection(claims, "aad");
            return new SimpleAuthenticationInfo(principals, null);
        } catch (Exception e) {
            throw new AuthenticationException("Could not validate bearer token", e);
        }
    }

    private Jws<Claims> validateAccessToken(String accessToken) throws Exception {
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKeyResolver(getSigningKeyResolver())
                    .requireAudience(getAudience())
                    .requireIssuer(getIssuer())
                    .build()
                    .parseClaimsJws(accessToken);

        } catch(SignatureException ex) {
            throw new JwtValidationException("Jwt validation failed: invalid signature", ex);
        } catch(ExpiredJwtException ex) {
            throw new JwtValidationException("Jwt validation failed: access token us expired", ex);
        } catch(MissingClaimException ex) {
            throw new JwtValidationException("Jwt validation failed: missing required claim", ex);
        } catch(IncorrectClaimException ex) {
            throw new JwtValidationException("Jwt validation failed: required claim has incorrect value", ex);
        }

        return claims;
    }

    public SigningKeyResolver getSigningKeyResolver() throws Exception {
        if (this.signingKeyResolver == null) {
            this.signingKeyResolver = new SigningKeyResolver(getAuthority());
        }
        return this.signingKeyResolver;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(final String audience) {
        this.audience = audience;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(final String issuer) {
        this.issuer = issuer;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(final String authority) {
        this.authority = authority;
    }
}
