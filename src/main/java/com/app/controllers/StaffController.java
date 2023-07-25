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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.staff.StaffBodyDTO;
import com.app.entities.Staff;
import com.app.services.Staff.StaffServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/staff")
public class StaffController extends BaseControllerImpl<Staff, StaffServiceImpl> {

  /* REGISTER RRHH */

  @PostMapping("")
  @PreAuthorize("hasRole('rrhh')")
  public ResponseEntity<?> register(@Valid @RequestBody StaffBodyDTO staffbody, BindingResult bindingResult)
      throws Exception {
    try {

      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }

      return ResponseEntity.status(HttpStatus.CREATED).body(service.register(staffbody));

    } catch (Exception e) {
      String msg = e.getMessage();

      if (msg.equals("USER_NOT_FOUND") || msg.equals("BRANCH_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
      } else if (msg.equals("STAFF_ALREADY_REGISTERED_WITH_THIS_USER_ID") || msg.equals("INVALID_ROL")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
      }
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

  /* FIND BY ID */

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('rrhh') or hasRole('supervisor')")
  public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    } catch (Exception e) {
      if (e.getMessage().equals("STAFF_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  /* UPDATE */
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('rrhh')")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody StaffBodyDTO staff, BindingResult bindingResult)
      throws Exception {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.update(id, staff));
    } catch (Exception e) {
      String msg = e.getMessage();
      if (msg.equals("STAFF_NOT_FOUND") || msg.equals("USER_NOT_FOUND") || msg.equals("BRANCH_NOT_FOUND")
          || msg.equals("INVALID_ROL")) {
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  /* LIST */
  @GetMapping("")
  @PreAuthorize("hasRole('rrhh') or hasRole('supervisor')")
  public ResponseEntity<?> list(Pageable pageable) throws Exception {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.list(pageable));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

}
