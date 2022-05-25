package com.kbaje.eshop.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.models.Authority;
import com.kbaje.eshop.services.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtTokenProviderTests {

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);

    private JwtTokenProvider provider = new JwtTokenProvider(userRepository);

    private AppUser testUser;

    @BeforeEach
    public void setUp() {
        testUser = AppUser.create("username", "test@test.com", "P@ssword1");

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
    }

    @Test
    public void shouldReturnCorrectAccessToken() {
        String token = provider.getAccessToken(testUser.getId(), List.of("USER"));
        Claims claims = Jwts.parser()
                .setSigningKey(JwtTokenProvider.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        assert claims.getSubject().equals(testUser.getId().toString());
        assert claims.get("username").equals(testUser.getUsername());
        assert claims.get("email").equals(testUser.getEmail());
        assert claims.get("roles").equals(List.of("USER"));
    }

    @Test
    public void shouldReturnCorrectAuthentication() {
        String token = provider.getAccessToken(testUser.getId(), List.of("USER"));
        UsernamePasswordAuthenticationToken auth = provider.getAuthentication(token);

        assert auth.getPrincipal().equals(testUser);
        assert auth.getCredentials() == null;
        assert auth.getAuthorities().contains(Authority.USER);
    }
}
