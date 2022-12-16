package com.w2m.application.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2m.application.auth.JwtUtils;
import com.w2m.application.auth.UserCredentiales;
import com.w2m.application.auth.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserCredentiales userCredentiales = new UserCredentiales();

        try {
            userCredentiales = new ObjectMapper().readValue(request.getReader(), UserCredentiales.class);
        } catch (IOException e) {
            log.error("\nERROR JwtAuthenticationFilter: {}", e.getMessage());
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userCredentiales.getName(),
                userCredentiales.getPass(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String strToken = JwtUtils.creationToken(userDetails.getName(), userDetails.getUsername());

        response.addHeader("Authorization", "Bearer " + strToken);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
