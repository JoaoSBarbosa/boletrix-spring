package com.joaobarbosadev.boletrix.core.service.token.providers.jjwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.joaobarbosadev.boletrix.core.service.token.jjwt")
public class JjwtConfigProperties {

    private String accessTokenSigningKey;
    private Long accessTokenExpirationInSeconds;
    private String refreshTokenSigningKey;
    private Long refreshTokenExpirationInSeconds;

    public String getAccessTokenSigningKey() {
        return accessTokenSigningKey;
    }

    public Long getRefreshTokenExpirationInSeconds() {
        return refreshTokenExpirationInSeconds;
    }

    public String getRefreshTokenSigningKey() {
        return refreshTokenSigningKey;
    }

    public Long getAccessTokenExpirationInSeconds() {
        return accessTokenExpirationInSeconds;
    }

    public void setAccessTokenSigningKey(String accessTokenSigningKey) {
        this.accessTokenSigningKey = accessTokenSigningKey;
    }

    public void setRefreshTokenExpirationInSeconds(Long refreshTokenExpirationInSeconds) {
        this.refreshTokenExpirationInSeconds = refreshTokenExpirationInSeconds;
    }

    public void setRefreshTokenSigningKey(String refreshTokenSigningKey) {
        this.refreshTokenSigningKey = refreshTokenSigningKey;
    }

    public void setAccessTokenExpirationInSeconds(Long accessTokenExpirationInSeconds) {
        this.accessTokenExpirationInSeconds = accessTokenExpirationInSeconds;
    }
}
