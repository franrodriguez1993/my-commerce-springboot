package com.app.entities;

import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "buyer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Audited
public class Buyer extends BaseEntity {

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  @Valid
  private User user;

  // validator
  @NotNull
  // sql
  @Column(name = "credit_card")
  private boolean creditCard;

}
