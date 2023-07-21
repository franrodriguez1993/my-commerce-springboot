package com.app.dto.product;

import java.util.List;

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
public class ProductBodyDTO extends _DTOEntity {

  @NotNull(message = "Name cannot be null")
  @NotBlank(message = "Name cannot be empty")
  @Size(min = 3, max = 150, message = "Name has to be 5 - 150 characters")
  private String name;

  @NotNull(message = "Price cannot be null")
  @Min(value = 1, message = "Minimum price should be 1")
  @Max(value = 999999, message = "Max price should be 999999")
  private double price;

  private List<BranchProductBodyDTO> stock;

  private String image;

  @NotNull(message = "Weight cannot be null")
  @Min(value = 0, message = "Weight should be higher than 0")
  @Max(value = 1000, message = "Weight should be less than 1000")
  private double weight;

  @NotNull(message = "brand_id cannot be null")
  private Long brand_id;

  @NotNull(message = "category_id cannot be null")
  private Long category_id;

}
