package com.app.services.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.product.ProductBodyDTO;
import com.app.dto.product.ProductDTO;
import com.app.entities.Product;
import com.app.services.base.BaseService;

public interface ProductService extends BaseService<Product, Long> {

  ProductDTO create(ProductBodyDTO product) throws Exception;

  Page<Product> listByBrand(String brand, Pageable pageable) throws Exception;

  Page<Product> listByCategory(String category, Pageable pageable) throws Exception;
}
