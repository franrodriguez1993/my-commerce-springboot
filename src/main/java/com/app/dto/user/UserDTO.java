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

public class UserDTO extends _DTOEntity {
  private String name;
  private String lastname;
  private String email;
  private int dni;
  private AddressDTO address;
}
