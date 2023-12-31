package com.app.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Category;

public interface CategoryRepository extends BaseRepository<Category, Long> {

  /* FIND BY NAME */
  @Query(value = "SELECT * FROM category WHERE name= :categoryname", nativeQuery = true)
  Optional<Category> findByName(@Param("categoryname") String categoryname);

  @Query(value = "SELECT * FROM category", nativeQuery = true)
  Page<Category> listAll(Pageable pageable);
}
