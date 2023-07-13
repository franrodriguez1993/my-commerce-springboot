package com.app.controllers;

import java.io.Serializable;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.entities.BaseEntity;

import jakarta.validation.Valid;

public interface BaseController<E extends BaseEntity, ID extends Serializable> {

  public ResponseEntity<?> getAll();

  public ResponseEntity<?> getOne(@PathVariable ID id);

  public ResponseEntity<?> save(@Valid @RequestBody E entity, BindingResult bindingResult);

  public ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);

  public ResponseEntity<?> delete(@PathVariable ID id);
}
