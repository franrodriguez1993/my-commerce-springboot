package com.app.dto.user;

import com.app.dto._DTOEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerDTO extends _DTOEntity {

  private Long id;
  private UserDTO user;
  private boolean creditCard;

}
