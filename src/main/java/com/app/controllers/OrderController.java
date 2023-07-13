package com.app.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Order;
import com.app.services.order.OrderServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/order")
public class OrderController extends BaseControllerImpl<Order, OrderServiceImpl> {

  /* === CREATE ORDER === */
  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody Order order) throws Exception {
    try {

      return ResponseEntity.status(HttpStatus.CREATED).body(service.create(order));

    } catch (Exception e) {
      if (e.getMessage().equals("PRODUCT_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      } else if (e.getMessage().equals("INVALID_QUANTITY") || e.getMessage().equals("INVALID_AMOUNT")
          || e.getMessage().equals("INVALID_TOTAL_AMOUNT")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  /* === LIST ORDER === */
  @GetMapping("/list/{bid}")
  public ResponseEntity<?> listOrders(@PathVariable Long bid, Pageable pageable) throws Exception {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.listOrders(bid, pageable));

    } catch (Exception e) {
      switch (e.getMessage()) {
        case "USER_NOT_FOUND": {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
        default: {
          return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());

        }
      }
    }
  }

}
