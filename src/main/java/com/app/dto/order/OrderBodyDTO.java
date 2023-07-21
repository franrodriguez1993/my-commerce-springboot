package com.app.dto.order;

import java.util.List;

import com.app.dto._DTOEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBodyDTO extends _DTOEntity {

  private Long user_id;
  private Long branch_id;
  private boolean creditCard;
  private int installments;
  private List<OrderDetailBodyDTO> details;
  private double totalAmount;
}
