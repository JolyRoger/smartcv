package org.torquemada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class SmartResumeApplication {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain apiChain(ServerHttpSecurity http) {
        http.authorizeExchange(ex -> ex.anyExchange().authenticated())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartResumeApplication.class, args);
    }
}
