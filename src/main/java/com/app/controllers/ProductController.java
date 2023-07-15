package com.app.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.product.ProductBodyDTO;
import com.app.entities.Product;
import com.app.services.product.ProductServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/product")
public class ProductController extends BaseControllerImpl<Product, ProductServiceImpl> {

  /* CREATE PRODUCT */

  @PostMapping("")
  public ResponseEntity<?> create(@Valid @RequestBody ProductBodyDTO product, BindingResult bindingResult) {
    try {
      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }

      return ResponseEntity.status(HttpStatus.CREATED).body(service.create(product));
    } catch (Exception e) {
      if (e.getMessage().equals("CATEGORY_NOT_FOUND") || e.getMessage().equals("BRAND_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  /* UPDATE PRODUCT */

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@Valid @RequestBody ProductBodyDTO product, @PathVariable Long id,
      BindingResult bindingResult) {
    try {

      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }

      return ResponseEntity.status(HttpStatus.OK).body(service.update(id, product));

    } catch (Exception e) {
      String errorMessage = e.getMessage();
      if (errorMessage.equals("PRODUCT_NOT_FOUND") || errorMessage.equals("BRAND_NOT_FOUND")
          || errorMessage.equals("CATEGORY_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  /* LIST BY BRAND */

  @GetMapping("/brand")
  public ResponseEntity<?> findByBrand(@RequestParam String brand, Pageable pageable) {
    try {

      return ResponseEntity.status(HttpStatus.OK).body(service.listByBrand(brand, pageable));

    } catch (Exception e) {
      if (e.getMessage().equals("BRAND_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("BRAND_NOT_FOUND");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("BAD GATEWAY");
      }
    }
  }
  /* LIST BY CATEGORY */

  @GetMapping("/category")
  public ResponseEntity<?> findByCategory(@RequestParam String category, Pageable pageable) {
    try {

      return ResponseEntity.status(HttpStatus.OK).body(service.listByCategory(category, pageable));

    } catch (Exception e) {
      if (e.getMessage().equals("CATEGORY_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CATEGORY_NOT_FOUND");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("BAD GATEWAY");
      }
    }
  }

}
