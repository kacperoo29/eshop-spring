package com.kbaje.eshop.configuration;

import com.kbaje.eshop.filters.AccessTokenPreAuthFilter;
import com.kbaje.eshop.services.JwtTokenProvider;
import com.kbaje.eshop.services.UserService;

import com.kbaje.eshop.services.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final RequestMatcher PROTECTED_ULRS = new OrRequestMatcher(
        new AntPathRequestMatcher("/api/checkout/**")
    );

    private final RequestMatcher ADMIN_URLS = new OrRequestMatcher(
        new AntPathRequestMatcher("/api/product/create"),
        new AntPathRequestMatcher("/api/product/edit/**"),
        new AntPathRequestMatcher("/api/product/remove/**")
    );

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new AccessTokenPreAuthFilter(new JwtTokenProvider(this.userRepository)), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers(PROTECTED_ULRS)
                .authenticated()
                .requestMatchers(ADMIN_URLS)
                .hasRole("ADMIN")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }
}
