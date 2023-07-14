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

import com.app.dto.user.BuyerBodyDTO;
import com.app.entities.Buyer;
import com.app.services.buyer.BuyerServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/buyer")
public class BuyerController extends BaseControllerImpl<Buyer, BuyerServiceImpl> {

  // @Override
  @PostMapping("/register")
  public ResponseEntity<?> save(@Valid @RequestBody BuyerBodyDTO buyer, BindingResult bindingResult) {
    try {
      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }

      return ResponseEntity.status(HttpStatus.OK).body(service.register(buyer));
    } catch (Exception e) {
      switch (e.getMessage()) {
        case "EMAIL_IN_USE": {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_IN_USE");
        }
        case "DNI_IN_USE": {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DNI_IN_USE");
        }
        default: {
          return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
        }
      }

    }

  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));

    } catch (Exception e) {
      if (e.getMessage().equals("BUYER_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("BUYER_NOT_FOUND");
      }
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

  @GetMapping("/list")
  public ResponseEntity<?> listBuyers(Pageable pageable) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.listBuyers(pageable));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

}
