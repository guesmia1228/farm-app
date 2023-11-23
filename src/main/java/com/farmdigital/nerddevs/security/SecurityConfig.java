package com.farmdigital.nerddevs.security;

import com.farmdigital.nerddevs.Filters.JwtFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private  final AuthenticationProvider authenticationProvider;
    private  final JwtFilter jwtFilter;
       AccessDeniedHandler accessDeniedHandler;
@Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
    httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((req)->req
                    .requestMatchers("/api/v1/farm_digital/super/**")
                    .permitAll()
                    .requestMatchers("/api/v1/agri_connect/verify/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()

            )
            .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex->ex.accessDeniedHandler(accessDeniedHandler));
    return httpSecurity.build();

}

}
