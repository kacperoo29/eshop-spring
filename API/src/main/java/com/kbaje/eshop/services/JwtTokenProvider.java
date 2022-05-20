package com.kbaje.eshop.services;

import java.util.List;
import java.util.UUID;

import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.services.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtTokenProvider implements AccessTokenProvider {

    private final String SECRET_KEY = "d3yQ_W?eQ^AL$y6q";

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getAccessToken(UUID userId, List<String> roles) {
        AppUser user = userRepository.findById(userId).get();

        String token = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(userId.toString())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("roles", roles)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        return token;
    }

    @Override
    public Authentication getAuthentication(String accessToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(accessToken)
                .getBody();

        String userId = claims.getSubject();
        String username = claims.get("username", String.class);
        UserDetails user = userRepository.findByUsername(username); 
        
        return new UsernamePasswordAuthenticationToken(userId, null, user.getAuthorities());
    }

}
