package com.app.entities.audit;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.app.config.CustomRevisionListener;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "REVISION_INFO")
@RevisionEntity(CustomRevisionListener.class)
@Data
public class Revision implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_seq")
  @SequenceGenerator(name = "revision_seq", sequenceName = "rbac.seq_revision_id")
  @RevisionNumber
  private int Id;

  @Column(name = "REVISION_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  @RevisionTimestamp
  private Date date;
}
