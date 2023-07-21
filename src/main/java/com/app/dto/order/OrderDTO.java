package com.app.dto.order;

import java.util.Date;
import java.util.List;

import com.app.dto._DTOEntity;
import com.app.dto.branch.BranchDTO;
import com.app.dto.user.UserDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends _DTOEntity {

  private Long id;
  private UserDTO user;
  private BranchDTO branch;
  private int installments;
  private Date date;
  private List<OrderDetailsDTO> details;
  private double totalAmount;

}
