package com.app.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.category.CategoryDTO;
import com.app.entities.Category;
import com.app.services.category.CategoryServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/category")
public class CategoryController extends BaseControllerImpl<Category, CategoryServiceImpl> {

  @PostMapping("")
  public ResponseEntity<?> save(@Valid @RequestBody CategoryDTO categorydto, BindingResult bindingResult) {
    try {

      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(categorydto));

    } catch (Exception e) {
      if (e.getMessage().equals("CATEGORY_ALREADY_EXISTS")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SERVER_ERROR");
      }
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findByID(@PathVariable Long id) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.findByID(id));
    } catch (Exception e) {
      if (e.getMessage().equals("CATEGORY_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SERVER_ERROR");
      }
    }
  }

  @GetMapping("")
  public ResponseEntity<?> listAll(Pageable pageable) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.listAll(pageable));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SERVER_ERROR");
    }
  }

}
