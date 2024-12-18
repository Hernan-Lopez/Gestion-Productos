package com.hernan.gestionproductos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable()
	        .headers().frameOptions().disable()
	        .and()
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()
	            .requestMatchers("/api/users/**").hasRole("ADMIN")
                .requestMatchers("/api/products/**").hasAnyRole("USER")
                .requestMatchers("/api/statistics/**").hasAnyRole("USER", "ADMIN")
	            .anyRequest().authenticated()
	        )
	        .httpBasic();

	    return http.build();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
