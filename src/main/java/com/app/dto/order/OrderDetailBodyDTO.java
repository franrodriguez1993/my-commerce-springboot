package com.app.dto.order;

import com.app.dto._DTOEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailBodyDTO extends _DTOEntity {

  private Long product_id;
  private int quantity;
  private double subtotal;
}
