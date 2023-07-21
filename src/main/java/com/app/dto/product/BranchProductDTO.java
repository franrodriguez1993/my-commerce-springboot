package com.app.dto.product;

import com.app.dto._DTOEntity;
import com.app.dto.branch.BranchDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchProductDTO extends _DTOEntity {

  private Long id;
  private ProductDTO product;
  private BranchDTO branch;
  private int quantity;
}
