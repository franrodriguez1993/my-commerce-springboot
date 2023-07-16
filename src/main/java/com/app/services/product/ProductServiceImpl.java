package com.app.services.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.product.ProductBodyDTO;
import com.app.dto.product.ProductDTO;
import com.app.dto.product.ProductMapper;
import com.app.entities.Brand;
import com.app.entities.Category;
import com.app.entities.Product;
import com.app.repositories.BaseRepository;
import com.app.repositories.BrandRepository;
import com.app.repositories.CategoryRepository;
import com.app.repositories.ProductRepository;
import com.app.services.base.BaseServiceImpl;
import com.app.util.ImageKitManager;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  BrandRepository brandRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  ImageKitManager ikm;

  public ProductServiceImpl(BaseRepository<Product, Long> baseRepository) {
    super(baseRepository);
  }

  /** LIST BY BRAND **/

  @Override
  public Page<ProductDTO> listByBrand(String brand, Pageable pageable) throws Exception {

    try {

      String brandname = brand.replace("_", " ");

      Optional<Brand> optionalBrand = brandRepository.findByName(brandname);

      if (optionalBrand.isPresent()) {

        Brand searchedBrand = optionalBrand.get();

        Page<Product> productList = productRepository.listByBrand(searchedBrand.getId(), pageable);
        return ProductMapper.INSTANCE.toProductsDTO(productList);

      } else {
        throw new Exception("BRAND_NOT_FOUND");
      }

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /** LIST BY CATEGORY **/

  @Override
  public Page<ProductDTO> listByCategory(String category, Pageable pageable) throws Exception {
    try {

      String categoryname = category.replace("_", " ");

      Optional<Category> optionalCategory = categoryRepository.findByName(categoryname);

      if (optionalCategory.isPresent()) {

        Category searchedCategory = optionalCategory.get();

        Page<Product> productList = productRepository.listByCategory(searchedCategory.getId(), pageable);
        return ProductMapper.INSTANCE.toProductsDTO(productList);

      } else {
        throw new Exception("CATEGORY_NOT_FOUND");
      }

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* CREATE PRODUCT */

  @Override
  public ProductDTO create(ProductBodyDTO product) throws Exception {
    try {

      // check Category:
      Optional<Category> category = categoryRepository.findById(product.getCategory_id());
      if (!category.isPresent()) {
        throw new Exception("CATEGORY_NOT_FOUND");
      }

      // Check brand:
      Optional<Brand> brand = brandRepository.findById(product.getBrand_id());
      if (!brand.isPresent()) {
        throw new Exception("BRAND_NOT_FOUND");
      }

      Product entity = ProductMapper.INSTANCE.toProductFromBody(product);
      entity.setCategory(category.get());
      entity.setBrand(brand.get());

      Product newProduct = productRepository.save(entity);
      return ProductMapper.INSTANCE.toProductDTO(newProduct);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* UPDATE PRODUCT */

  @Override
  public ProductDTO update(Long id, ProductBodyDTO productdto) throws Exception {
    try {
      Optional<Product> po = productRepository.findById(id);

      // Check if exists:
      if (!po.isPresent()) {
        throw new Exception("PRODUCT_NOT_FOUND");
      }
      // Map to product:
      Product updateProduct = ProductMapper.INSTANCE.toProductFromBody(productdto);
      updateProduct.setId(id);

      // check brand:
      Optional<Brand> brand = brandRepository.findById(productdto.getBrand_id());
      if (!brand.isPresent()) {
        throw new Exception("BRAND_NOT_FOUND");
      }
      updateProduct.setBrand(brand.get());

      // Check category:
      Optional<Category> category = categoryRepository.findById(productdto.getCategory_id());
      if (!category.isPresent()) {
        throw new Exception("CATEGORY_NOT_FOUND");
      }
      updateProduct.setCategory(category.get());

      return ProductMapper.INSTANCE.toProductDTO(productRepository.save(updateProduct));

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* UPLOAD IMAGE */

  @Override
  public ProductDTO uploadImage(Long id, MultipartFile file) throws Exception {
    try {
      Optional<Product> po = productRepository.findById(id);

      if (!po.isPresent()) {
        throw new Exception("PRODUCT_NOT_FOUND");
      }
      Product product = po.get();

      String imageUrl = ikm.uploadImage(file);
      product.setImage(imageUrl);

      return ProductMapper.INSTANCE.toProductDTO(productRepository.save(product));

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
