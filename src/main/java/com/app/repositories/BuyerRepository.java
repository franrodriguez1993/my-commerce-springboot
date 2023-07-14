package com.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Buyer;

public interface BuyerRepository extends BaseRepository<Buyer, Long> {

  @Query(value = "SELECT * FROM buyer", nativeQuery = true)
  Page<Buyer> pageBuyers(@Param("filter") String filter, Pageable pageable);

}
