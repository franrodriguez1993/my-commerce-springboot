package com.app.dto.brand;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.app.entities.Brand;

@Mapper
public interface BrandMapper {
  BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

  Brand toBrand(BrandDTO branddto);

  BrandDTO toBrandDTO(Brand brand);

  // page brandDTOs
  default Page<BrandDTO> toBrandDTOs(Page<Brand> brands) {
    return brands.map(this::toBrandDTO);
  }

}
