package com.kbaje.eshop.models;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
public class AppUser extends BaseEntity implements UserDetails {
    
    @Column(unique = true)
    private String username;
    
    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(targetClass = Authority.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    private List<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    protected AppUser() {

    }

    protected AppUser(String username, String email, String password) {
        super();

        this.email = email;
        this.password = password;
        this.authorities = new LinkedList<>();
    }

    public static AppUser create(String username, String email, String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser user = new AppUser(username, email, encoder.encode(password));
        user.authorities.add(Authority.USER);

        return user;
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

}
