package com.kbaje.eshop.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AccessTokenProvider {
    
    String getAccessToken(UUID userId, List<String> roles);

    UsernamePasswordAuthenticationToken getAuthentication(String accessToken);

}
