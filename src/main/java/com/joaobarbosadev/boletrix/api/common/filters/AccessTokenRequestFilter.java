package com.joaobarbosadev.boletrix.api.common.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaobarbosadev.boletrix.core.exception.customizations.TokenServiceException;
import com.joaobarbosadev.boletrix.core.exception.response.StandardError;
import com.joaobarbosadev.boletrix.core.models.CustomError;
import com.joaobarbosadev.boletrix.core.service.token.interfaces.TokenService;
import com.joaobarbosadev.boletrix.core.utils.Util;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

import static com.joaobarbosadev.boletrix.core.utils.JwtBearerUtils.AUTHORIZATION_HEADER;
import static com.joaobarbosadev.boletrix.core.utils.JwtBearerUtils.TOKEN_TYPE;

@Component
public class AccessTokenRequestFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public AccessTokenRequestFilter(ObjectMapper objectMapper, TokenService tokenService, UserDetailsService userDetailsService) {
        this.objectMapper = objectMapper;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {

            tryDolFilterINternal(request, response, filterChain);
        } catch (TokenServiceException e) {

            var status = HttpStatus.UNAUTHORIZED;
            CustomError body = new CustomError();
            body.setStatus(status.value());
            body.setMessage(e.getLocalizedMessage());
            body.setDetails( e.getClass().getSimpleName());
            body.setTimestamp(Util.getFormattedInstance(new Date()));
            body.setError(status.getReasonPhrase());

            var json = objectMapper.writeValueAsString(body);
            response.setStatus(status.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

        }

    }


    private void tryDolFilterINternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var token = "";
        var email = "";
        var authentizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (isIsPresentToken(authentizationHeader)) {
            token = authentizationHeader.substring(TOKEN_TYPE.length());
            email = tokenService.getSubjectFromAccessToken(token);
        }
        // verifica se email já não esta no contexto
        if(isIsEmailNotInContent(email)) {
            setAuthentication(request,email);
        }

        filterChain.doFilter(request, response);

    }

    private void setAuthentication(HttpServletRequest request, String email) {
        var userDetails = userDetailsService.loadUserByUsername(email);
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    private static boolean isIsEmailNotInContent(String email){
        return email != null && !email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null;
    }
    private static boolean isIsPresentToken(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE);
    }
}
