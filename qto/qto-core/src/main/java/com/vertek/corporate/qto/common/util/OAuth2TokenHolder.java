package com.vertek.corporate.qto.common.util;

/**
 * Convenience class for holding an OAuth2 access token, refresh token, and info needed to manage them.
 * @author mmeehan
 * @since 4.0.0
 */
public class OAuth2TokenHolder {

    /** Token URI. */
    private String tokenUri;

    /** Access token. */
    private String accessToken;

    /** Expires in. */
    private Integer expiresIn;

    /** Refresh token. */
    private String refreshToken;

    /** Auth: OAuth 2 Client ID. */
    private String clientId;

    /** Auth: OAuth 2 Client secret. */
    private String clientSecret;


    public String getTokenUri() {
        return tokenUri;
    }

    public void setTokenUri(final String tokenUri) {
        this.tokenUri = tokenUri;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(final Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
