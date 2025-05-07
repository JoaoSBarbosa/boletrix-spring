package com.joaobarbosadev.boletrix.api.common.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaobarbosadev.boletrix.core.exception.response.StandardError;
import com.joaobarbosadev.boletrix.core.models.CustomError;
import com.joaobarbosadev.boletrix.core.utils.Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class TokenAuthenticationEntryPointer implements AuthenticationEntryPoint {
    private final ObjectMapper mapper;
    public TokenAuthenticationEntryPointer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        var status = HttpStatus.UNAUTHORIZED;
        CustomError bodyError = new CustomError();

        bodyError.setStatus(status.value());
        bodyError.setTitle("Não foi possível autenticar o token");
        bodyError.setMessage(authException.getLocalizedMessage());
        bodyError.setTimestamp(Util.getFormattedInstance(new Date()));
        bodyError.setPath(request.getRequestURI());
        bodyError.setError(status.getReasonPhrase());
        bodyError.setDetails( authException.getClass().getSimpleName() );

        var json = mapper.writeValueAsString(bodyError);
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
