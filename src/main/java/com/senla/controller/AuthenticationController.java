package com.senla.controller;

import com.senla.model.dto.JwtRequest;
import com.senla.model.dto.JwtResponse;
import com.senla.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/auth")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) {
        jwtTokenUtils.validateJwtRequest(jwtRequest);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        String generatedToken = jwtTokenUtils.generateToken(jwtRequest.getUsername());
        return new JwtResponse(generatedToken);
    }
}
