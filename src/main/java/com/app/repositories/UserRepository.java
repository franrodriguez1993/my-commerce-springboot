package com.app.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.User;

public interface UserRepository extends BaseRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByDni(int dni);

  @Query(value = "SELECT * from user", nativeQuery = true)
  Page<User> listUsers(Pageable pageable);

}
