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
public class BranchProductAloneDTO extends _DTOEntity {
  private Long id;
  private ProductDTO product;
  private int quantity;
}
