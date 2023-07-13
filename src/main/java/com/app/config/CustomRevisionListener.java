package com.app.config;

import org.hibernate.envers.RevisionListener;

import com.app.entities.audit.Revision;

public class CustomRevisionListener implements RevisionListener {

  @Override
  public void newRevision(Object revisionEntity) {

    final Revision revision = (Revision) revisionEntity;
  }

}
