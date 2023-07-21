package com.app.services.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.product.BranchProductBodyDTO;
import com.app.dto.product.ProductBodyDTO;
import com.app.dto.product.ProductDTO;
import com.app.dto.product.ProductMapper;
import com.app.entities.Branch;
import com.app.entities.BranchProduct;
import com.app.entities.Brand;
import com.app.entities.Category;
import com.app.entities.Product;
import com.app.repositories.BaseRepository;
import com.app.repositories.BranchRepository;
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
  BranchRepository branchRepository;

  @Autowired
  ImageKitManager ikm;

  public ProductServiceImpl(BaseRepository<Product, Long> baseRepository) {
    super(baseRepository);
  }

  /* CREATE PRODUCT */

  @Override
  public ProductDTO create(ProductBodyDTO productbody) throws Exception {
    try {
      // convert dto in entity:
      Product entity = ProductMapper.INSTANCE.toProductFromBody(productbody);

      // check Category:
      Optional<Category> category = categoryRepository.findById(productbody.getCategory_id());
      if (!category.isPresent()) {
        throw new Exception("CATEGORY_NOT_FOUND");
      }

      // Check brand:
      Optional<Brand> brand = brandRepository.findById(productbody.getBrand_id());
      if (!brand.isPresent()) {
        throw new Exception("BRAND_NOT_FOUND");
      }

      // check all the branch_product stock:
      List<BranchProductBodyDTO> bpdto = productbody.getStock();
      List<BranchProduct> branchProducts = new ArrayList<BranchProduct>();

      for (BranchProductBodyDTO b : bpdto) {
        Optional<Branch> brancOptional = branchRepository.findById(b.getBranch_id());
        if (!brancOptional.isPresent()) {
          throw new Exception("BRANCH_NOT_FOUND");
        }
        BranchProduct bp = ProductMapper.INSTANCE.toBranchProduct(b);
        bp.setBranch(brancOptional.get()); // linking branch
        bp.setProduct(entity); // linking product
        branchProducts.add(bp); // adding to the list

      }

      entity.setCategory(category.get());
      entity.setBrand(brand.get());
      entity.setStock(branchProducts);

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
      // Old product:
      Product oldProduct = po.get();
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
      updateProduct.setStock(oldProduct.getStock());

      return ProductMapper.INSTANCE.toProductDTO(productRepository.save(updateProduct));

    } catch (Exception e) {
      System.out.println(e.getMessage());
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

  /* LIST PRODUCTS */

  @Override
  public Page<ProductDTO> listProducts(String brand, String category, Pageable pageable) throws Exception {
    try {
      // IF NOT CATEGORY AND NOT BRAND
      if (brand == null && category == null) {
        Page<Product> products = productRepository.listProduct(pageable);
        return ProductMapper.INSTANCE.toProductsDTO(products);
      }
      // IF JUST BRAND
      else if (brand != null && category == null) {

        return this.listByBrand(brand, pageable);

      }
      // IF JUST CATEGORY
      else if (brand == null && category != null) {
        return this.listByCategory(category, pageable);
      } else {
        return this.listByCategoryAndBrand(brand, category, pageable);
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public ProductDTO findByID(Long id) throws Exception {
    try {
      Optional<Product> product = productRepository.findById(id);
      if (!product.isPresent()) {
        throw new Exception("PRODUCT_NOT_FOUND");
      }
      return ProductMapper.INSTANCE.toProductDTO(product.get());

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /** LIST BY BRAND **/

  protected Page<ProductDTO> listByBrand(String brand, Pageable pageable) throws Exception {

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

  protected Page<ProductDTO> listByCategory(String category, Pageable pageable) throws Exception {
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

  /** LIST BY CATEGORY AND BRAND **/

  protected Page<ProductDTO> listByCategoryAndBrand(String brand, String category, Pageable pageable) throws Exception {
    try {
      String categoryname = category.replace("_", " ");
      Optional<Category> optionalCategory = categoryRepository.findByName(categoryname);
      String brandname = brand.replace("_", " ");
      Optional<Brand> optionalBrand = brandRepository.findByName(brandname);

      if (!optionalCategory.isPresent()) {
        throw new Exception("CATEGORY_NOT_FOUND");
      }
      if (!optionalBrand.isPresent()) {
        throw new Exception("BRAND_NOT_FOUND");
      }

      return ProductMapper.INSTANCE.toProductsDTO(productRepository
          .listByCategoryAndBrand(optionalCategory.get().getId(), optionalBrand.get().getId(), pageable));

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
