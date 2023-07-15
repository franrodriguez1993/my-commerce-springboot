package com.app.dto.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.app.entities.Product;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  // To product
  Product toProduct(ProductDTO productdto);

  // to productDTO
  ProductDTO toProductDTO(Product product);

  // to product From body:
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "brand", ignore = true)
  Product toProductFromBody(ProductBodyDTO product);

  default Page<ProductDTO> toProductsDTO(Page<Product> products) {
    return products.map(this::toProductDTO);
  }
}
