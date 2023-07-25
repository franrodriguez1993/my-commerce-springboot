package com.app.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.brand.BrandDTO;
import com.app.entities.Brand;
import com.app.services.brand.BrandServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/brand")
public class BrandController extends BaseControllerImpl<Brand, BrandServiceImpl> {

  @PostMapping("")
  @PreAuthorize("hasRole('supervisor')")
  public ResponseEntity<?> save(@Valid @RequestBody BrandDTO branddto, BindingResult bindingResult) {
    try {

      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(service.create(branddto));

    } catch (Exception e) {
      if (e.getMessage().equals("BRAND_ALREADY_EXISTS")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('supervisor') or hasRole('storer')")
  public ResponseEntity<?> getByID(@PathVariable Long id) {
    try {

      return ResponseEntity.status(HttpStatus.OK).body(service.findByID(id));
    } catch (Exception e) {
      if (e.getMessage().equals("BRAND_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

  @GetMapping("")
  @PreAuthorize("hasRole('supervisor') or hasRole('storer')")
  public ResponseEntity<?> getAll(Pageable pageable) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }
}
