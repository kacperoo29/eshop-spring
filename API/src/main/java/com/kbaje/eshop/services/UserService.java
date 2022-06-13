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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(UserService.class);

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
                        .orElseThrow(() -> {
                            logger.error("User with id {} not found", id);
                            return new EntityNotFoundException(AppUser.class, id);
                        }));
    }

    public AuthDto authenticate(AuthRequestDto authRequest) {
        AppUser user = userRepository.findByEmail(authRequest.email);

        if (user == null) {
            logger.info("User with email {} not found", authRequest.email);
            return new AuthDto(false, "", "User not found");
        }

        if (!passwordEncoder.matches(authRequest.password, user.getPassword())) {
            logger.warn("Password mismatch for user with email {}", authRequest.email);
            return new AuthDto(false, "", "Wrong password");
        }

        List<String> authorityList = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String accessToken = accessTokenProvider.getAccessToken(user.getId(), authorityList);

        logger.info("User with email {} authenticated", authRequest.email);
        return new AuthDto(true, accessToken, "Success");
    }

    public UserDto makeUserAdmin(UUID userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(AppUser.class, userId));
        user.makeAdmin();

        logger.info("User with id {} made admin", user.getId());
        return mapper.userToDto(userRepository.save(user));
    }

    public UserDto createUser(CreateUserDto payload) {
        AppUser maybeUser = userRepository.findByEmail(payload.email);
        if (maybeUser != null) {
            logger.warn("User with email {} already exists", payload.email);
            throw new UserAlreadyExistsException("User with email " + payload.email + " already exists");
        }

        maybeUser = userRepository.findByUsername(payload.username);
        if (maybeUser != null) {
            logger.warn("User with username {} already exists", payload.username);
            throw new UserAlreadyExistsException("User with username " + payload.username + " already exists");
        }

        AppUser user = AppUser.create(payload.username, payload.email, passwordEncoder.encode(payload.password));
        AppUser createdUser = userRepository.save(user);

        logger.info("User with id {} created", createdUser.getId());
        return mapper.userToDto(createdUser);
    }

    public UserDto getUserByEmail(String email) {
        AppUser user = userRepository.findByEmail(email);

        return mapper.userToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);

        if (user == null) {
            logger.warn("User with username {} not found", username);
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    public UserDto getCurrentUserDto() {
        return mapper.userToDto(getCurrentUser());
    }
}
