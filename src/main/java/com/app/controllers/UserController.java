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
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.user.UserBodyDTO;
import com.app.entities.User;
import com.app.services.user.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/user")
public class UserController extends BaseControllerImpl<User, UserServiceImpl> {

  @PostMapping("")
  public ResponseEntity<?> register(@Valid @RequestBody UserBodyDTO user, BindingResult bindingResult) {
    try {
      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }

      return ResponseEntity.status(HttpStatus.CREATED).body(service.register(user));
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
      if (e.getMessage().equals("USER_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

  @GetMapping("")
  public ResponseEntity<?> list(Pageable pageable) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.list(pageable));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@Valid @RequestBody UserBodyDTO user, @PathVariable Long id,
      BindingResult bindingResult) {
    try {

      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }

      return ResponseEntity.status(HttpStatus.OK).body(service.update(id, user));
    } catch (Exception e) {
      String msg = e.getMessage();
      if (msg.equals("USER_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } else if (msg.equals("MAIL_IN_USE") || msg.equals("DNI_IN_USE")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

}
