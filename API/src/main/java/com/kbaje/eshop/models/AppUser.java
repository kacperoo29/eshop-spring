package com.kbaje.eshop.models;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.kbaje.eshop.exceptions.EmptyFieldException;
import com.kbaje.eshop.exceptions.InvalidEmailException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class AppUser extends BaseEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    private List<Authority> authorities;

    protected AppUser() {

    }

    protected AppUser(String username, String email, String password) {
        super();

        this.email = email;
        this.password = password;
        this.username = username;
        this.authorities = new LinkedList<>();
    }

    public static AppUser create(String username, String email, String password) {

        if (email.isEmpty()) {
            throw new EmptyFieldException(AppUser.class, "email");
        }

        if (username.isEmpty()) {
            throw new EmptyFieldException(AppUser.class, "username");
        }

        if (password.isEmpty()) {
            throw new EmptyFieldException(AppUser.class, "password");
        }

        if (!Pattern.compile("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}$").matcher(email).matches()) {
            throw new InvalidEmailException(email);
        }

        AppUser user = new AppUser(username, email, password);
        user.authorities.add(Authority.USER);

        return user;
    }
    
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void makeAdmin() {
        if (!authorities.contains(Authority.ADMIN))
            authorities.add(Authority.ADMIN);
    }

}
