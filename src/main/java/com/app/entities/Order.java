package com.app.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.envers.Audited;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "order_sell")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Audited
public class Order extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "buyer_id")
  private Buyer buyer;

  @Column(name = "credit_card", nullable = false)
  private boolean creditCard;

  @Column(length = 2)
  private int installments = 1;

  @Column
  private Date date;

  @Column(name = "total_amount")
  private double totalAmount;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderDetail> details;

}
