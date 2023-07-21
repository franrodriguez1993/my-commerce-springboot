package com.app.dto.staff;

import com.app.dto._DTOEntity;
import com.app.dto.branch.BranchDTO;
import com.app.dto.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO extends _DTOEntity {
  private Long id;
  private UserDTO user;
  private BranchDTO branch;
  private double salary;
  private String laborDischarge;
  private String status;
  private String ban;
}
