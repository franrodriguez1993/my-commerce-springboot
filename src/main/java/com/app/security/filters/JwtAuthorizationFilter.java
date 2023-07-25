package com.app.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.security.jwt.JwtUtils;
import com.app.services.user.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // extract token:
    String tokenHeader = request.getHeader("Authorization");

    if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
      String token = tokenHeader.substring(7);
      if (jwtUtils.isTokenValid(token)) {
        Long id = jwtUtils.getIdFromToken(token);

        try {
          // get userDetails:
          UserDetails userDetails = userDetailsService.loadUserByID(id);

          // login in authorization:
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails.getUsername(), null, userDetails.getAuthorities());

          // this containt the authentication in the application:
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }

    }
    // if token is null or invalid (denied access):
    filterChain.doFilter(request, response);

  }

}
