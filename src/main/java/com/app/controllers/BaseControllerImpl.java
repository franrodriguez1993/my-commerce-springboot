package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.entities.BaseEntity;
import com.app.services.base.BaseServiceImpl;

import jakarta.validation.Valid;

public abstract class BaseControllerImpl<E extends BaseEntity, S extends BaseServiceImpl<E, Long>>
    implements BaseController<E, Long> {

  @Autowired
  protected S service;

  @Override
  @GetMapping("")
  public ResponseEntity<?> getAll() {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR_METHOD");
    }
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<?> getOne(Long id) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    } catch (Exception e) {
      switch (e.getMessage()) {
        case "NOT_FOUND": {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND");
        }
        default: {
          return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
        }
      }

    }
  }

  @Validated
  @Override
  @PostMapping("")
  public ResponseEntity<?> save(@Valid @RequestBody E entity, BindingResult bindingResult) {
    try {
      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }

      return ResponseEntity.status(HttpStatus.OK).body(service.save(entity));

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<?> update(Long id, E entity) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity));
    } catch (Exception e) {
      switch (e.getMessage()) {
        case "NOT_FOUND": {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND");
        }
        default: {
          return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
        }
      }
    }
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(Long id) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR_METHOD");
    }

  }

}
