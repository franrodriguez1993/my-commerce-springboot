package com.app.dto.product;

import com.app.dto._DTOEntity;
import com.app.dto.brand.BrandDTO;
import com.app.dto.category.CategoryDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends _DTOEntity {

  private Long id;
  private String name;
  private double price;
  private String image;
  private double weight;
  private CategoryDTO category;
  private BrandDTO brand;

}
