package com.app.dto.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.app.entities.Order;
import com.app.entities.OrderDetail;

@Mapper
public interface OrderMapper {

  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "date", ignore = true)
  @Mapping(target = "buyer", ignore = true)
  Order toOrderFroOrderBodyDTO(OrderBodyDTO obdto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "order", ignore = true)
  @Mapping(target = "product", ignore = true)
  OrderDetail toOrderDetailFromOrderDetailBody(OrderDetailBodyDTO odbdto);

  OrderDTO toOrderDTOFromOrder(Order order);
}
