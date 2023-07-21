package com.app.entities;

import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class User extends BaseEntity {

  @Column(nullable = false, length = 60)
  private String name;

  @Column(nullable = false, length = 60)
  private String lastname;

  @Column(nullable = false, length = 8, unique = true)
  private int dni;

  @Column(nullable = false, length = 60, unique = true)
  private String email;

  @Column(nullable = false, length = 60)
  private String password;

  @Column
  private String rol;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id")
  private Address address;

}
