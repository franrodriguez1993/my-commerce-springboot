package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.security.filters.JwtAuthenticationFilter;
import com.app.security.filters.JwtAuthorizationFilter;
import com.app.security.jwt.JwtUtils;
import com.app.services.user.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  JwtAuthorizationFilter jwtAuthorizationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager)
      throws Exception {

    JwtAuthenticationFilter JwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);

    JwtAuthenticationFilter.setAuthenticationManager(authenticationManager);

    return httpSecurity.csrf(csrf -> {
      csrf.disable();
    }).authorizeHttpRequests(auth -> {
      auth.requestMatchers(HttpMethod.GET, "/api/v1/product").permitAll();
      auth.requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll();
      auth.anyRequest().authenticated();
    })
        .sessionManagement(session -> {
          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        })

        .addFilter(JwtAuthenticationFilter)
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // authentication manager:
  @Bean
  AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
      throws Exception {
    return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder).and().build();

  }

}
