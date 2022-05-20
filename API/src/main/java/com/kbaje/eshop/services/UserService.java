package com.kbaje.eshop.services;

import java.util.UUID;
import java.util.stream.Collectors;

import com.kbaje.eshop.dto.AuthDto;
import com.kbaje.eshop.dto.AuthRequestDto;
import com.kbaje.eshop.dto.CreateUserDto;
import com.kbaje.eshop.dto.UserDto;
import com.kbaje.eshop.mapping.UserMapper;
import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.services.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccessTokenProvider accessTokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDto getById(UUID id) {
        return userMapper.mapToDto(userRepository.findById(id).get());
    }

    public AuthDto authenticate(AuthRequestDto authRequest) {
        AppUser user = userRepository.findByEmail(authRequest.email);

        if (user == null) {
            return new AuthDto(false, "", "User not found");
        }

        if (!passwordEncoder.matches(authRequest.password, user.getPassword())) {
            System.out.println(user.getPassword());
            System.out.println(passwordEncoder.encode(authRequest.password));
            System.out.println(passwordEncoder.encode(authRequest.password));
            return new AuthDto(false, "", "Wrong password");
        }

        return new AuthDto(true,
                accessTokenProvider.getAccessToken(user.getId(), user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList())),
                "Success");
    }

    public UserDto createUser(CreateUserDto payload) {
        AppUser user = AppUser.create(payload.username, payload.email, payload.password);
        AppUser createdUser = userRepository.save(user);

        return userMapper.mapToDto(createdUser);
    }

    public UserDto getUserByEmail(String email) {
        AppUser user = userRepository.findByEmail(email);

        return userMapper.mapToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        return user;
    }
}
