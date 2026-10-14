package org.generation.marify.config;

import lombok.RequiredArgsConstructor;
import org.generation.marify.Security.JwtAuthenticationFilter;
import org.generation.marify.models.User;
import org.generation.marify.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
   private final UserRepository userRepository;

   @Bean
   public UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new UsernameNotFoundException
                            ("Email no registrado " + email)
            );
            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
            );
        };
   }

   @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
       return config.getAuthenticationManager();
   }

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter
   )throws Exception {
       http
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests( auth ->
                       auth
                               .requestMatchers("/api/auth/**").permitAll()
                               .anyRequest().authenticated()
                       )
               .sessionManagement( session -> session
                       .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               )
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
       return http.build();
   }

}
