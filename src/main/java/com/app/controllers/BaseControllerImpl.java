package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;

import com.app.entities.BaseEntity;
import com.app.services.base.BaseServiceImpl;

public abstract class BaseControllerImpl<E extends BaseEntity, S extends BaseServiceImpl<E, Long>>
    implements BaseController<E, Long> {

  @Autowired
  protected S service;

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
