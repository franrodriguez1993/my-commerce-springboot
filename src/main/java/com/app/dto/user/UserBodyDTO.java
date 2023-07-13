package com.app.dto.user;

import com.app.dto._DTOEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

public class UserBodyDTO extends _DTOEntity {

  private Long id;

  @NotNull(message = "Name cannot be null")
  @NotBlank(message = "Name cannot be empty")
  @Size(min = 3, max = 60, message = "Name has to be 3 - 60 characters")
  private String name;

  @NotNull(message = "Lastname cannot be null")
  @NotBlank(message = "Lastname cannot be empty")
  @Size(min = 3, max = 60, message = "Lastname has to be 3 - 60 characters")
  private String lastname;

  @NotNull(message = "DNI cannot be null")
  @Min(value = 1000000, message = "DNI must be greater than 1.000.000")
  @Max(value = 99999999, message = "DNI must be less than 99.999.999")
  private int dni;

  @NotNull(message = "Email cannot be null")
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Invalid email format")
  private String email;

  @NotNull(message = "password cannot be null")
  @NotBlank(message = "password cannot be empty")
  private String password;

  @Valid
  private AddressBodyDTO address;
}
