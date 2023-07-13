package com.app.dto.user;

import com.app.dto._DTOEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressBodyDTO extends _DTOEntity {
  private Long id;

  @NotNull(message = "street cannot be null")
  @NotBlank(message = "street cannot be blank")
  @Size(min = 3, max = 80, message = "street has to be 3 - 80 characters")
  private String street;

  @NotNull(message = "number cannot be null")
  @Min(value = 1, message = "minimum number should be 1")
  @Max(value = 99999, message = "maximun number should be 99999")
  private int number;

  @NotNull(message = "city cannot be null")
  @NotBlank(message = "city cannot be blank")
  @Size(min = 3, max = 80, message = "city has to be 3 - 80 characters")
  private String city;
}
