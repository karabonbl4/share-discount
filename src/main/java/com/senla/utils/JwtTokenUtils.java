package com.senla.utils;

import com.senla.dao.UserRepository;
import com.senla.model.dto.JwtRequest;
import com.senla.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    private final UserRepository userRepository;
    @Value("${jwt.lifecycle.minutes}")
    private int jwtLifecycleMinutes;
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (long) jwtLifecycleMinutes * 1000 * 60))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token) && getUsernameFromToken(token) != null;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public void validateJwtRequest(JwtRequest jwtRequest) {
        String username = jwtRequest.getUsername();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with username: %s not found!", username));
        }
    }

    private boolean isTokenExpired(String token) {
        Date expirationTime = getClaimFromToken(token, Claims::getExpiration);
        return expirationTime.before(new Date());
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        return claimsTFunction.apply(claims);
    }
}
