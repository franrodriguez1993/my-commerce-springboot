package com.app.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.BranchProduct;

public interface BranchProductRepository extends BaseRepository<BranchProduct, Long> {

  @Query(value = "SELECT * FROM branch_product WHERE product_id = :pid AND branch_id = :bid", nativeQuery = true)
  Optional<BranchProduct> findByProductAndBranch(@Param("bid") Long bid, @Param("pid") Long pid);

  /* LIST STOCK */

  @Query(value = "SELECT * FROM branch_product WHERE branch_id = :bid", nativeQuery = true)
  Page<BranchProduct> listProductByStock(@Param("bid") Long bid, Pageable pageable);

}
