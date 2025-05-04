package com.joaobarbosadev.boletrix.core.service.token.providers.jjwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.joaobarbosadev.boletrix.core.services.token.jjwt")
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
}
