package com.app.services.base;

import java.io.Serializable;

import com.app.entities.BaseEntity;

public interface BaseService<E extends BaseEntity, ID extends Serializable> {

  public boolean delete(ID id) throws Exception;

}
