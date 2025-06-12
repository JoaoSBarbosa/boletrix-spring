package com.joaobarbosadev.boletrix.core.service.token.providers.jjwt;

import com.joaobarbosadev.boletrix.core.exception.customizations.TokenServiceException;
import com.joaobarbosadev.boletrix.core.models.auth.UserDetailsImpl;
import com.joaobarbosadev.boletrix.core.models.domain.Role;
import com.joaobarbosadev.boletrix.core.models.domain.UserSystem;
import com.joaobarbosadev.boletrix.core.service.token.interfaces.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JjwtTokenService implements TokenService {
    private final JjwtConfigProperties configProperties;

    public JjwtTokenService(JjwtConfigProperties configProperties) {
        this.configProperties = configProperties;
    }


    @Override
    public String generatedAccessToken(UserDetailsImpl user) {
        Map<String, Object> claims = generateClaims(user);

        return generateToken(
                claims,
                user.getUserSystem().getEmail(),
                configProperties.getAccessTokenExpirationInSeconds(),
                configProperties.getAccessTokenSigningKey()
        );
    }

    @Override
    public String getSubjectFromAccessToken(String token) {
        return getClaims(token, configProperties.getAccessTokenSigningKey()).getSubject();
    }

    @Override
    public String generateRefreshToken(UserDetailsImpl user) {
        Map<String, Object> claims = generateClaims(user);
        return generateToken(claims, user.getUserSystem().getEmail(), configProperties.getRefreshTokenExpirationInSeconds(), configProperties.getRefreshTokenSigningKey());
    }

    @Override
    public String getSubjectFromRefreshToken(String refreshToken) {
        return getClaims(refreshToken, configProperties.getRefreshTokenSigningKey()).getSubject();
    }

    @Override
    public void inlidateAcessToken(String... tokens) {

    }


    private Claims getClaims(String token, String signingKey) {
        try {
            return tryGetClaims(token, signingKey);
        } catch (JwtException e) {
            throw new TokenServiceException(e.getLocalizedMessage());
        }
    }

    private Claims tryGetClaims(String token, String signingKey) {
        //if(){}
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(signingKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(Map<String, Object> claims, String subject, Long expirationInSeconds, String signingKey) {
        var today = Instant.now();
        var expirationDate = today.plusSeconds(expirationInSeconds);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject( subject )
                .setIssuedAt(Date.from(today))
                .setExpiration(Date.from(expirationDate))
                .signWith(Keys.hmacShaKeyFor(signingKey.getBytes()))
                .compact();
    }

    private Map<String, Object> generateClaims(UserDetailsImpl userSystem) {
        Map<String, Object> claims = new HashMap<>();
        UserSystem user = userSystem.getUserSystem();
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("id", user.getId());
        claims.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return claims;
    }


}
