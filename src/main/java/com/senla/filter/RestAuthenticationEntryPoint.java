package com.senla.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(null, LocalDateTime.now(), HttpStatus.UNAUTHORIZED);

        if (e.getClass().isAssignableFrom(BadCredentialsException.class)) {
            errorResponse.setMessage("Password incorrect!");
        } else {
            errorResponse.setMessage(e.getMessage());
        }

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        OutputStream out = httpServletResponse.getOutputStream();

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(out, errorResponse);
        log.error(String.valueOf(errorResponse));
        out.flush();
    }
}