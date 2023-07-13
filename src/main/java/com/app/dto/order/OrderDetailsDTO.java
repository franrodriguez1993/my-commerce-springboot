package com.app.dto.order;

import com.app.dto._DTOEntity;
import com.app.dto.product.ProductDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO extends _DTOEntity {
  private Long id;
  private ProductDTO product;
  private OrderDTO order;
  private int quantity;
  private double subtotal;
}
