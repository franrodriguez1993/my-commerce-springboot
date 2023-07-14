package com.app.services.base;

import java.io.Serializable;

import com.app.entities.BaseEntity;
import com.app.repositories.BaseRepository;

import jakarta.transaction.Transactional;

public abstract class BaseServiceImpl<E extends BaseEntity, ID extends Serializable> implements BaseService<E, ID> {

  protected BaseRepository<E, ID> baseRepository;

  public BaseServiceImpl(BaseRepository<E, ID> baseRepository) {
    this.baseRepository = baseRepository;
  }

  @Override
  @Transactional
  public boolean delete(ID id) throws Exception {

    try {
      if (baseRepository.existsById(id)) {
        baseRepository.deleteById(id);
        return true;
      } else {
        return false;
      }

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
