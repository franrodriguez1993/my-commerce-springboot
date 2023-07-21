package com.app.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Staff;

public interface StaffRepository extends BaseRepository<Staff, Long> {

  @Query(value = "SELECT * FROM staff", nativeQuery = true)
  Page<Staff> list(Pageable pageable);

  /* FIND BY USER ID */
  @Query(value = "SELECT * FROM staff WHERE user_id = :uid", nativeQuery = true)
  Optional<Staff> findByUserId(@Param("uid") Long uid);

}
