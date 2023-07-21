package com.app.dto.branch;

import com.app.dto._DTOEntity;
import com.app.dto.user.AddressBodyDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchBodyDTO extends _DTOEntity {

  @Valid
  private AddressBodyDTO address;

}
