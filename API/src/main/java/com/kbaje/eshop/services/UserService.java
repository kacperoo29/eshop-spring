package com.kbaje.eshop.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.kbaje.eshop.dto.AuthDto;
import com.kbaje.eshop.dto.AuthRequestDto;
import com.kbaje.eshop.dto.CreateUserDto;
import com.kbaje.eshop.dto.UserDto;
import com.kbaje.eshop.exceptions.EntityNotFoundException;
import com.kbaje.eshop.exceptions.UserAlreadyExistsException;
import com.kbaje.eshop.mapping.MapperProfile;
import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.services.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private MapperProfile mapper;

    private AccessTokenProvider accessTokenProvider;

    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public UserService(UserRepository userRepository, MapperProfile mapper, AccessTokenProvider accessTokenProvider) {
        this.passwordEncoder = passwordEncoder();
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.accessTokenProvider = accessTokenProvider;
    }

    public AppUser getCurrentUser() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UserDto getById(UUID id) {
        return mapper.userToDto(
                userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(AppUser.class, id)));
    }

    public AuthDto authenticate(AuthRequestDto authRequest) {
        AppUser user = userRepository.findByEmail(authRequest.email);

        if (user == null) {
            return new AuthDto(false, "", "User not found");
        }

        if (!passwordEncoder.matches(authRequest.password, user.getPassword())) {
            return new AuthDto(false, "", "Wrong password");
        }

        List<String> authorityList = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String accessToken = accessTokenProvider.getAccessToken(user.getId(), authorityList);

        return new AuthDto(true, accessToken, "Success");
    }

    public UserDto makeUserAdmin(UUID userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(AppUser.class, userId));
        user.makeAdmin();

        return mapper.userToDto(userRepository.save(user));
    }

    public UserDto createUser(CreateUserDto payload) {
        AppUser maybeUser = userRepository.findByEmail(payload.email);
        if (maybeUser != null) {
            throw new UserAlreadyExistsException("User with email " + payload.email + " already exists");
        }

        maybeUser = userRepository.findByUsername(payload.username);
        if (maybeUser != null) {
            throw new UserAlreadyExistsException("User with username " + payload.username + " already exists");
        }

        AppUser user = AppUser.create(payload.username, payload.email, passwordEncoder.encode(payload.password));
        AppUser createdUser = userRepository.save(user);

        return mapper.userToDto(createdUser);
    }

    public UserDto getUserByEmail(String email) {
        AppUser user = userRepository.findByEmail(email);

        return mapper.userToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        return user;
    }

    public UserDto getCurrentUserDto() {
        return mapper.userToDto(getCurrentUser());
    }
}
