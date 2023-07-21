package com.app.entities;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff extends BaseEntity {

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "branch_id")
  private Branch branch;

  @Column(nullable = false)
  private double salary;

  @Column(name = "labor_discharge")
  private Date laborDischarge;

  @Column(nullable = false)
  private String status;

  @Column
  private String ban; // bank account number

}
