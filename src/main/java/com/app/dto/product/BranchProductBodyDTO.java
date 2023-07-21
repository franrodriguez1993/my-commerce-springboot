package com.app.dto.product;

import com.app.dto._DTOEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchProductBodyDTO extends _DTOEntity {

  private Long product_id;
  private Long branch_id;
  private int quantity;
}
