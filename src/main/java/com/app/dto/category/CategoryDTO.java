package com.app.dto.category;

import com.app.dto._DTOEntity;

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
public class CategoryDTO extends _DTOEntity {
  private Long id;
  @NotNull(message = "Name cannot be null")
  @NotBlank(message = "Name cannot be empty")
  @Size(min = 3, max = 60, message = "Name has to be 3 - 50 characters")
  private String name;
}
