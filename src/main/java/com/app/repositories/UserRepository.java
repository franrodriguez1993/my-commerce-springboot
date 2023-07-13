package com.app.repositories;

import java.util.Optional;

import com.app.entities.User;

public interface UserRepository extends BaseRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByDni(int dni);

}
