package com.app.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Brand;

public interface BrandRepository extends BaseRepository<Brand, Long> {

  @Query(value = "SELECT * FROM brand WHERE name= :brandname", nativeQuery = true)
  Optional<Brand> findByName(@Param("brandname") String brandname);

  @Query(value = "SELECT * FROM brand", nativeQuery = true)
  Page<Brand> listBrands(Pageable pageable);
}
