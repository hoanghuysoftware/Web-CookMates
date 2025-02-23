package com.cookmates.categoryservice.config;

import com.cookmates.categoryservice.utils.JwtEntryPoint;
import com.cookmates.categoryservice.utils.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtEntryPoint jwtEntryPoint;
    private final JwtFilter jwtFilter;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> null; // Không sử dụng UserDetails, chỉ dùng JWT
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint));

        return http.build();
    }
}
