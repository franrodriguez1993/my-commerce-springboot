package com.app.entities;

import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brand")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Audited
public class Brand extends BaseEntity {

  @Column(nullable = false, length = 50)
  private String name;

}
