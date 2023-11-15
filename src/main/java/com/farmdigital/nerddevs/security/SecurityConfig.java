package com.farmdigital.nerddevs.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
@Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
    httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((req)->req
                    .requestMatchers("/api/v1/farm_digital/super/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            );
    return httpSecurity.build();

}

}
