package com.app.dto.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.app.entities.Buyer;

@Mapper
public interface BuyerMapper {

  BuyerMapper INSTANCE = Mappers.getMapper(BuyerMapper.class);

  // to buyerDTO

  BuyerDTO toBuyerDto(Buyer buyer);

  // to buyers DTO

  default Page<BuyerDTO> toBuyerDtos(Page<Buyer> buyers) {
    return buyers.map(this::toBuyerDto);
  };

  // to buyer entity
  Buyer toBuyer(BuyerBodyDTO buyerbody);

}
