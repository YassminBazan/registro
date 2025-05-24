package com.proyecto.proyecto.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filtro(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 🔴 Permite POST desde Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/usuarios/registro",
                    "/api/usuarios/correo/**"
                ).permitAll()
                .anyRequest().permitAll() // También puedes usar authenticated() si tienes login
            );

        return http.build();
    }
}
