package com.app.services.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.order.OrderBodyDTO;
import com.app.dto.order.OrderDTO;
import com.app.entities.Order;
import com.app.services.base.BaseService;

public interface OrderService extends BaseService<Order, Long> {

  OrderDTO create(OrderBodyDTO order) throws Exception;

  Page<Order> listOrders(Long uid, Pageable pageable) throws Exception;
}
