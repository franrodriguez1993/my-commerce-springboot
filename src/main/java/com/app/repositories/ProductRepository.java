package com.app.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Product;

public interface ProductRepository extends BaseRepository<Product, Long> {

  @Query(value = "SELECT * FROM product", nativeQuery = true)
  Page<Product> listProduct(Pageable pageable);

  /* LIST BY BRAND */
  @Query(value = "SELECT * FROM product where brand_id = :bid", nativeQuery = true)
  Page<Product> listByBrand(@Param("bid") Long bid, Pageable pageable);

  /* LIST BY CATEGORY */
  @Query(value = "SELECT * FROM product where category_id = :cid", nativeQuery = true)
  Page<Product> listByCategory(@Param("cid") Long cid, Pageable pageable);

  /* LIST BY CATEGORY AND BRAND */
  @Query(value = "SELECT * FROM product where category_id = :cid AND brand_id = :bid", nativeQuery = true)
  Page<Product> listByCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid, Pageable pageable);

}
