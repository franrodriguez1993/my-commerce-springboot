package com.app.dto.branch;

import com.app.dto._DTOEntity;
import com.app.dto.user.AddressDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO extends _DTOEntity {

  private Long id;
  private AddressDTO address;
}
