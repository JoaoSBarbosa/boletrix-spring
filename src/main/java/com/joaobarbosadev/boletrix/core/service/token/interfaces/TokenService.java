package com.joaobarbosadev.boletrix.core.service.token.interfaces;

import com.joaobarbosadev.boletrix.core.models.domain.UserSystem;

public interface TokenService {

    String generatedAccessToken(UserSystem user );
    String getSubjectFromAccessToken(String token); // pega o subject de um token
    String generateRefreshToken(String subject); // gera o refress token
    String getSubjectFromRefreshToken(String refreshToken); // pega o subject do refress token

    void inlidateAcessToken(String... tokens);
}
