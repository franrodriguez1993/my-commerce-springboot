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
public class AddressDTO extends _DTOEntity {

  private String street;
  private int number;
  private String city;
}
