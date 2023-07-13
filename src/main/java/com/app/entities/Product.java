package com.app.entities;

import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Product extends BaseEntity {

  @Column(nullable = false, length = 150)
  private String name;

  @Column
  private double price;

  @Column
  private int stock;

  @Column
  private String image;

  @Column(nullable = false)
  private double weight;

  @ManyToOne
  @JoinColumn(name = "brand_id")
  private Brand brand;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
