package com.app.dto.order;

import java.util.Date;
import java.util.List;

import com.app.dto._DTOEntity;
import com.app.dto.user.BuyerDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends _DTOEntity {

  private Long id;
  private BuyerDTO buyer;
  private int installment;
  private Date date;
  private List<OrderDetailsDTO> details;
  private double totalAmount;

}
