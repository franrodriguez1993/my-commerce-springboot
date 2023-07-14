package com.app.services.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.dto.order.OrderBodyDTO;
import com.app.dto.order.OrderDTO;
import com.app.dto.order.OrderDetailBodyDTO;
import com.app.dto.order.OrderMapper;
import com.app.entities.Buyer;
import com.app.entities.Order;
import com.app.entities.OrderDetail;
import com.app.entities.Product;
import com.app.repositories.BaseRepository;
import com.app.repositories.BuyerRepository;
import com.app.repositories.OrderDetailRepository;
import com.app.repositories.OrderRepository;
import com.app.repositories.ProductRepository;
import com.app.repositories.UserRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected OrderRepository orderRepository;

  @Autowired
  protected OrderDetailRepository orderDetailRepository;

  @Autowired
  protected ProductRepository productRepository;

  @Autowired
  protected BuyerRepository buyerRepository;

  public OrderServiceImpl(BaseRepository<Order, Long> baseRepository) {
    super(baseRepository);

  }

  /* === CREATE ORDER === */

  @Override
  public OrderDTO create(OrderBodyDTO orderBody) throws Exception {

    try {

      // from orderbody to order entity:
      Order order = OrderMapper.INSTANCE.toOrderFroOrderBodyDTO(orderBody);

      // Check buyer:
      Optional<Buyer> buyer = buyerRepository.findById(orderBody.getBuyer_id());

      if (!buyer.isPresent()) {
        throw new Exception("BUYER_NOT_FOUND");
      }
      order.setBuyer(buyer.get());

      // VALIDATE AND UPDATE PRODUCTS QUANTITY:
      List<OrderDetailBodyDTO> detailList = orderBody.getDetails();
      double totalAmount = 0;

      List<OrderDetail> odEntities = new ArrayList<OrderDetail>();

      for (OrderDetailBodyDTO od : detailList) {
        Optional<Product> product = productRepository.findById(od.getProduct_id());
        // check existence:
        if (!product.isPresent()) {
          throw new Exception("PRODUCT_NOT_FOUND");
        }
        Product p = product.get();
        // check stock:
        if (p.getStock() < od.getQuantity()) {
          throw new Exception("INVALID_QUANTITY");
        }
        // check amount:
        double subtotal = p.getPrice() * od.getQuantity();
        if (subtotal != od.getSubtotal()) {
          throw new Exception("INVALID_AMOUNT");
        }

        // update quantity and sum subtotal:
        p.setStock(p.getStock() - od.getQuantity());
        productRepository.save(p);

        // pass to dto to entity detail:
        OrderDetail ode = OrderMapper.INSTANCE.toOrderDetailFromOrderDetailBody(od);
        ode.setProduct(p);
        ode.setOrder(order); // bidirection
        odEntities.add(ode); // add entity to list

        totalAmount += od.getSubtotal();
      }
      // Check total amount:
      if (totalAmount != order.getTotalAmount()) {
        throw new Exception("INVALID_TOTAL_AMOUNT");
      }

      order.setDate(new Date()); // set timestamp
      order.setDetails(odEntities);

      // create and return order:
      Order newOrder = orderRepository.save(order);

      return OrderMapper.INSTANCE.toOrderDTOFromOrder(newOrder);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* === LIST USER ORDERS === */

  @Override
  public Page<Order> listOrders(Long bid, Pageable pageable) throws Exception {
    try {
      Optional<Buyer> ob = buyerRepository.findById(bid);

      if (!ob.isPresent())
        throw new Exception("USER_NOT_FOUND");

      return orderRepository.listOrders(bid, pageable);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
