package com.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Order;

public interface OrderRepository extends BaseRepository<Order, Long> {

  @Query(value = "SELECT * FROM order_sell WHERE buyer_id= :bid", nativeQuery = true)
  Page<Order> listOrders(@Param("bid") Long bid, Pageable pageable);
}
