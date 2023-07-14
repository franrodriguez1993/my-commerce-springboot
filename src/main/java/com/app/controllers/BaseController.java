package com.app.controllers;

import java.io.Serializable;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;

import com.app.entities.BaseEntity;

public interface BaseController<E extends BaseEntity, ID extends Serializable> {

  public ResponseEntity<?> delete(@PathVariable ID id);
}
