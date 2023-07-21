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

import com.app.dto.branch.BranchBodyDTO;
import com.app.dto.product.BranchProductBodyDTO;
import com.app.entities.Branch;
import com.app.services.branch.BranchServiceImp;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/branch")
public class BranchController extends BaseControllerImpl<Branch, BranchServiceImp> {

  /* CREATE A BRANCH */
  @PostMapping("")
  public ResponseEntity<?> save(@Valid @RequestBody BranchBodyDTO branch, BindingResult bindingResult)
      throws Exception {
    try {

      if (bindingResult.hasErrors()) {
        String errorMsg = bindingResult.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
      }

      return ResponseEntity.status(HttpStatus.CREATED).body(service.create(branch));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

  /* LIST ALL BRANCHES */
  @GetMapping("")
  public ResponseEntity<?> list(Pageable pegeable) throws Exception {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.list(pegeable));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    } catch (Exception e) {
      if (e.getMessage().equals("BRANCH_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  /* GET STOCK BY BRANCH */
  @GetMapping("/{id}/stock")
  public ResponseEntity<?> getStockById(@PathVariable Long id, Pageable pageable) throws Exception {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.listStockByBranch(id, pageable));
    } catch (Exception e) {
      if (e.getMessage().equals("BRANCH_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  /* UPDATE PRODUCT BRANCH STOCK */

  @PutMapping("/{bid}/stock/{pid}")
  public ResponseEntity<?> updateStock(@PathVariable Long bid, @PathVariable Long pid,
      @RequestBody BranchProductBodyDTO bpbdto)
      throws Exception {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.updateStock(bid, pid, bpbdto));
    } catch (Exception e) {
      if (e.getMessage().equals("BRANCH_NOT_FOUND") || e.getMessage().equals("PRODUCT_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

}
