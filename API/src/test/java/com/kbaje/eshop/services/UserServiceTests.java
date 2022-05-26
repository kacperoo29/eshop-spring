package com.kbaje.eshop.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kbaje.eshop.dto.AuthDto;
import com.kbaje.eshop.dto.AuthRequestDto;
import com.kbaje.eshop.dto.CreateUserDto;
import com.kbaje.eshop.dto.UserDto;
import com.kbaje.eshop.exceptions.EntityNotFoundException;
import com.kbaje.eshop.exceptions.UserAlreadyExistsException;
import com.kbaje.eshop.mapping.MapperProfile;
import com.kbaje.eshop.models.AppUser;
import com.kbaje.eshop.services.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTests {

    public static final String SECRET_KEY = "d3yQ_W?eQ^AL$y6q";

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccessTokenProvider accessTokenProvider;

    private MapperProfile mapperProfile = Mappers.getMapper(MapperProfile.class);

    private UserService userService;

    private List<AppUser> testUsers;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        accessTokenProvider = mock(AccessTokenProvider.class);
        userService = new UserService(userRepository, mapperProfile, accessTokenProvider);

        testUsers = new ArrayList<>(List.of(
                AppUser.create("testU1", "test1@email.com", passwordEncoder.encode("P@ssword1")),
                AppUser.create("testU2", "test2@email.com", passwordEncoder.encode("P@ssword2")),
                AppUser.create("testU3", "test3@email.com", passwordEncoder.encode("P@ssword3")),
                AppUser.create("testU4", "test4@email.com", passwordEncoder.encode("P@ssword4"))));

        when(userRepository.findById(any(UUID.class))).thenAnswer(value -> testUsers.stream()
                .filter(u -> u.getId().equals(value.getArgument(0, UUID.class))).findFirst());
        when(userRepository.findAll()).thenReturn(testUsers);
        when(userRepository.save(any(AppUser.class))).thenAnswer(answer -> {
            if (testUsers.stream().noneMatch(u -> u.getId().equals(answer.getArgument(0, AppUser.class).getId()))) {
                testUsers.add(answer.getArgument(0, AppUser.class));
            }
            return answer.getArgument(0, AppUser.class);
        });
        when(accessTokenProvider.getAccessToken(any(UUID.class), anyList())).thenAnswer(value -> {
            return value.getArgument(0, UUID.class).toString();
        });
        when(userRepository.findByEmail(any(String.class))).thenAnswer(value -> {
            String email = value.getArgument(0, String.class);

            return testUsers.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElseGet(() -> null);
        });
        when(userRepository.findByUsername(any(String.class))).thenAnswer(value -> {
            String username = value.getArgument(0, String.class);

            return testUsers.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElseGet(() -> null);
        });
    }

    @Test
    public void shouldFindById() {
        UUID userId = testUsers.get(0).getId();

        UserDto user = userService.getById(userId);

        assert user.id.equals(userId);
        assert user.username.equals(testUsers.get(0).getUsername());
        assert user.email.equals(testUsers.get(0).getEmail());
    }

    @Test
    public void shouldThrowWhenNotFound() {
        UUID userId = UUID.randomUUID();

        assertThrows(EntityNotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    public void shouldCreateUser() {
        CreateUserDto payload = new CreateUserDto("testU5", "test5@email.com", "P@ssword5");

        UserDto user = userService.createUser(payload);

        assert user.id != null;
        assert user.username.equals(payload.username);
        assert user.email.equals(payload.email);
    }

    @Test
    public void shouldntCreateUserWithDuplicateEmail() {
        CreateUserDto payload = new CreateUserDto("testU5", testUsers.get(0).getEmail(), "P@ssword5");

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(payload));
    }

    @Test
    public void shouldntCreateUserWithDuplicateUsername() {
        CreateUserDto payload = new CreateUserDto(testUsers.get(0).getUsername(), "test7@email.com", "P@ssword5");

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(payload));
    }

    @Test
    public void shouldAuthenticate() {
        AuthRequestDto payload = new AuthRequestDto();
        payload.email = testUsers.get(0).getEmail();
        payload.password = "P@ssword1";

        AuthDto authResult = userService.authenticate(payload);

        assert authResult.isSucessfull;
    }

    @Test
    public void shouldntAuthenticateWhenUserNotFound() {
        AuthRequestDto payload = new AuthRequestDto();
        payload.email = "notauser@email.com";
        payload.password = "P@ssword1";

        AuthDto authResult = userService.authenticate(payload);

        assert !authResult.isSucessfull;
    }

    @Test
    public void shouldntAuthenticateWithWrongPassword() {
        AuthRequestDto payload = new AuthRequestDto();
        payload.email = testUsers.get(0).getEmail();
        payload.password = "wrongpassword";

        AuthDto authResult = userService.authenticate(payload);

        assert !authResult.isSucessfull;
    }

    @Test
    public void shouldLoadUserByUsername() {
        String username = testUsers.get(0).getUsername();

        UserDetails user = userService.loadUserByUsername(username);

        assert user.getUsername().equals(username);
    }

    @Test
    public void shouldThrowWhenUsernameNotFound() {
        String username = "notauser";

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}
