package com.app.entities;

import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Address extends BaseEntity {

  @Column(nullable = false, length = 80)
  private String street;

  @Column(nullable = false)
  private int number;

  @Column(nullable = false, length = 80)
  private String city;

}
