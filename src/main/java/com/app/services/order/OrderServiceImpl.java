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
import com.app.entities.Branch;
import com.app.entities.BranchProduct;
import com.app.entities.Order;
import com.app.entities.OrderDetail;
import com.app.entities.Product;
import com.app.entities.User;
import com.app.repositories.BaseRepository;
import com.app.repositories.BranchProductRepository;
import com.app.repositories.BranchRepository;
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
  protected BranchProductRepository branchProductRepository;

  @Autowired
  protected BranchRepository branchRepository;

  public OrderServiceImpl(BaseRepository<Order, Long> baseRepository) {
    super(baseRepository);

  }

  /* === CREATE ORDER === */

  @Override
  public OrderDTO create(OrderBodyDTO orderBody) throws Exception {

    try {

      // from orderbody to order entity:
      Order order = OrderMapper.INSTANCE.toOrderFroOrderBodyDTO(orderBody);

      // Check user:
      Optional<User> user = userRepository.findById(orderBody.getUser_id());

      if (!user.isPresent()) {
        throw new Exception("BUYER_NOT_FOUND");
      }
      order.setUser(user.get());

      // check branch:
      Optional<Branch> branchOptional = branchRepository.findById(orderBody.getBranch_id());

      if (!branchOptional.isPresent()) {
        throw new Exception("BRANCH_NOT_FOUND");
      }

      // VALIDATE AND UPDATE PRODUCTS QUANTITY:
      List<OrderDetailBodyDTO> detailList = orderBody.getDetails();
      double totalAmount = 0;

      // list of order details entities checked
      List<OrderDetail> odEntities = new ArrayList<OrderDetail>();

      for (OrderDetailBodyDTO od : detailList) {
        Optional<Product> productOptional = productRepository.findById(od.getProduct_id());
        // check if product exists:
        if (!productOptional.isPresent()) {
          throw new Exception("PRODUCT_NOT_FOUND");
        }
        Product product = productOptional.get();

        // check if product has stock in the branch:
        Optional<BranchProduct> branchProductOptional = branchProductRepository.findByProductAndBranch(
            orderBody.getBranch_id(),
            product.getId());

        if (!branchProductOptional.isPresent()) {
          throw new Exception("PRODUCT_HAS_NO_STOCK_IN_BRANCH");
        }
        BranchProduct branchProduct = branchProductOptional.get();

        // check stock:
        if (branchProduct.getQuantity() < od.getQuantity()) {
          throw new Exception("INVALID_QUANTITY");
        }

        // check amount:
        double subtotal = product.getPrice() * od.getQuantity();
        if (subtotal != od.getSubtotal()) {
          throw new Exception("INVALID_AMOUNT");
        }

        // update quantity and sum subtotal:
        branchProduct.setQuantity(branchProduct.getQuantity() - od.getQuantity());
        branchProductRepository.save(branchProduct);

        // pass to dto to entity detail:
        OrderDetail ode = OrderMapper.INSTANCE.toOrderDetailFromOrderDetailBody(od);
        ode.setProduct(product);
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
      order.setBranch(branchOptional.get());

      // create and return order:
      Order newOrder = orderRepository.save(order);

      return OrderMapper.INSTANCE.toOrderDTOFromOrder(newOrder);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* === LIST USER ORDERS === */

  @Override
  public Page<Order> listOrders(Long uid, Pageable pageable) throws Exception {
    try {
      Optional<User> ob = userRepository.findById(uid);

      if (!ob.isPresent())
        throw new Exception("USER_NOT_FOUND");

      return orderRepository.listOrders(uid, pageable);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
