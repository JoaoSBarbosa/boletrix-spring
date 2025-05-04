package com.joaobarbosadev.boletrix.api.common.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaobarbosadev.boletrix.core.exception.customizations.TokenServiceException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.joaobarbosadev.boletrix.core.utils.JwtBearerUtils.AUTHORIZATION_HEADER;
import static com.joaobarbosadev.boletrix.core.utils.JwtBearerUtils.TOKEN_TYPE;

@Component
public class AccessTokenRequestFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    public AccessTokenRequestFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {

        } catch (TokenServiceException e) {

        }

    }


    private void tryDolFilterINternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var token = "";
        var email = "";
        var authentizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (isIsPresentToken(authentizationHeader)) {
            token = authentizationHeader.substring(TOKEN_TYPE.length());

        }

    }

    private static boolean isIsPresentToken(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE);
    }
}
