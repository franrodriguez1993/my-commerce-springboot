package com.app.services.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.product.ProductBodyDTO;
import com.app.dto.product.ProductDTO;
import com.app.entities.Product;
import com.app.services.base.BaseService;

public interface ProductService extends BaseService<Product, Long> {

  ProductDTO create(ProductBodyDTO product) throws Exception;

  ProductDTO update(Long id, ProductBodyDTO product) throws Exception;

  ProductDTO uploadImage(Long id, MultipartFile file) throws Exception;

  Page<ProductDTO> listProducts(String brand, String category, Pageable pageable) throws Exception;

  ProductDTO findByID(Long id) throws Exception;

}
