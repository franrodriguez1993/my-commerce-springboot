package com.app.dto.user;

import com.app.dto._DTOEntity;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BuyerBodyDTO extends _DTOEntity {
  private Long id;
  @Valid
  private UserBodyDTO user;
  private boolean creditCard;
}
