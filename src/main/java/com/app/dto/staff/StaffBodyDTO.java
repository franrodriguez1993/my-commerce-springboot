package com.app.dto.staff;

import com.app.dto._DTOEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StaffBodyDTO extends _DTOEntity {

  @NotNull
  private Long user_id;

  @NotNull
  private Long branch_id;

  @Min(value = 1000, message = "salary should be higher than 1000")
  @Max(value = 99999, message = "salary should be less than 99999")
  private double salary;

  private String laborDischarge;

  @NotNull
  @NotBlank
  private String status;

  @NotNull
  @NotBlank
  private String ban;

  @NotNull
  @NotBlank
  private String rol;
}
