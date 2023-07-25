package com.app.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.app.entities.User;
import com.app.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j // logger
public class JwtUtils {
  @Value("${jwt.secret.key}")
  private String secretKey;
  @Value("${jwt.time.expiration}")
  private String timeExpiration;

  @Autowired
  UserRepository userRepository;

  // generate token:
  public String generateAccessToken(String username) {

    Optional<User> user = userRepository.findByEmail(username);

    String userId = Long.toString(user.get().getId());

    return Jwts.builder()
        .setSubject(userId)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
        .signWith(getSignatureKey(), SignatureAlgorithm.HS256).compact();
  }

  // Get token signature:
  public Key getSignatureKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // validate token:
  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();

      return true;
    } catch (Exception e) {
      log.error("Invalid Token - Error: ".concat(e.getMessage()));
      return false;
    }
  }

  // get claims (token data info)
  public Claims extractAlClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();
  }

  // get One Claim (generic function)
  public <T> T getClaim(String token, Function<Claims, T> claimsFunction) {
    Claims claims = extractAlClaims(token);
    return claimsFunction.apply(claims);
  }

  // get id from token:
  public Long getIdFromToken(String token) {
    return Long.parseLong(getClaim(token, Claims::getSubject));
  }

}
