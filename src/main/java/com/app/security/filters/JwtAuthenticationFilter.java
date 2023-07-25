package com.app.security.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.entities.User;
import com.app.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private JwtUtils jwtUtils;

  // inyected:
  public JwtAuthenticationFilter(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  // When someone is trying to login in the api:
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {

    User user = null;
    String email = "";
    String password = "";

    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      user = mapper.readValue(request.getInputStream(), User.class);
      System.out.println(request.getInputStream());
      email = user.getEmail();
      password = user.getPassword();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

    return getAuthenticationManager().authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {

    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
        .getPrincipal();

    String token = jwtUtils.generateAccessToken(user.getUsername());
    response.addHeader("Authorization", token);

    // create a hashmap:
    Map<String, Object> httpResponse = new HashMap<>();
    httpResponse.put("Token", token);
    httpResponse.put("Message", "Login successfully");
    httpResponse.put("Email", user.getUsername());

    // Convert the hashmap in a json:
    response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));

    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().flush();

    super.successfulAuthentication(request, response, chain, authResult);
  }

}
