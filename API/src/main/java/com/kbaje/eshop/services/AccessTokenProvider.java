package com.kbaje.eshop.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;

public interface AccessTokenProvider {
    
    String getAccessToken(UUID userId, List<String> roles);

    Authentication getAuthentication(String accessToken);

}
