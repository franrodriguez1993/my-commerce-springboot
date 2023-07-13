package com.app.dto.user;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.app.entities.Buyer;

@Mapper
public interface BuyerMapper {

  BuyerMapper INSTANCE = Mappers.getMapper(BuyerMapper.class);

  // to buyerDTO

  BuyerDTO toBuyerDto(Buyer buyer);

  // to buyers DTO

  List<BuyerDTO> toBuyerDtos(List<Buyer> buyers);

  // to buyer entity
  Buyer toBuyer(BuyerBodyDTO buyerbody);

}
