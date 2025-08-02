package org.torquemada.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder, @Value("${application-password}") String password) {
        UserDetails user = User
            .withUsername("admin")
            .password(passwordEncoder.encode(password))
            .roles("USER")
            .build();

        return new MapReactiveUserDetailsService(user);
    }
}