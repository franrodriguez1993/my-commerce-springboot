package com.app.services.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.entities.User;
import com.app.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  // LOAD user by username
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND"));

    String emailUser = user.getEmail();
    String passUser = user.getPassword();
    String rolUser = user.getRol();

    Set<String> arrayRoles = new HashSet<String>();
    arrayRoles.add(rolUser);

    Collection<? extends GrantedAuthority> authorities = arrayRoles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role))).collect(Collectors.toSet());

    return new org.springframework.security.core.userdetails.User(emailUser, passUser, true, true, true, true,
        authorities);

  }

  // LOAD user by id
  public UserDetails loadUserByID(Long id) throws Exception {
    User user = userRepository.findById(id).orElseThrow(() -> new Exception("USER_NOT_FOUND"));

    String emailUser = user.getEmail();
    String passUser = user.getPassword();
    String rolUser = user.getRol();

    Set<String> arrayRoles = new HashSet<String>();
    arrayRoles.add(rolUser);

    Collection<? extends GrantedAuthority> authorities = arrayRoles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role))).collect(Collectors.toSet());

    return new org.springframework.security.core.userdetails.User(emailUser, passUser, true, true, true, true,
        authorities);

  }

}
