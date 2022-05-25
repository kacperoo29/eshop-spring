package com.kbaje.eshop.models;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kbaje.eshop.exceptions.EmptyFieldException;
import com.kbaje.eshop.exceptions.InvalidEmailException;

import org.junit.jupiter.api.Test;

public class AppUserTests {

    @Test
    public void shouldThrowWhenEmptyUsername() {
        EmptyFieldException e = assertThrows(EmptyFieldException.class,
                () -> AppUser.create("", "test@test.com", "P@ssword1"));

        assert e.getMessage().equals("Field username in class AppUser can't be empty");
    }

    @Test
    public void shouldThrowWhenEmptyEmail() {
        EmptyFieldException e = assertThrows(EmptyFieldException.class,
                () -> AppUser.create("username", "", "P@ssword1"));

        assert e.getMessage().equals("Field email in class AppUser can't be empty");
    }

    @Test
    public void shouldThrowWhenEmptyPassword() {
        EmptyFieldException e = assertThrows(EmptyFieldException.class,
                () -> AppUser.create("username", "test@test.com", ""));
        assert e.getMessage().equals("Field password in class AppUser can't be empty");
    }

    @Test
    public void shouldThrowWhenInvalidEmail() {
        InvalidEmailException e = assertThrows(InvalidEmailException.class,
                () -> AppUser.create("username", "test", "P@ssword1"));

        assert e.getMessage().equals("Invalid email: test");
    }

    @Test
    public void shouldCreateAppUser() {
        AppUser user = AppUser.create("username", "test@test.com", "P@ssword1");

        assert user.getUsername().equals("username");
        assert user.getEmail().equals("test@test.com");
        assert user.getPassword().equals("P@ssword1");
    }

    @Test
    public void shouldMakeAdmin() {
        AppUser user = AppUser.create("username", "test@test.com", "P@ssword1");

        user.makeAdmin();

        assert user.getAuthorities().contains(Authority.ADMIN);
    }

    @Test
    public void shouldntAddDuplicateAutohrity() {
        AppUser user = AppUser.create("username", "test@test.com", "P@ssword1");

        user.makeAdmin();
        user.makeAdmin();

        assert user.getAuthorities().contains(Authority.ADMIN);
        assert user.getAuthorities().contains(Authority.USER);
        assert user.getAuthorities().size() == 2;
    }
}
