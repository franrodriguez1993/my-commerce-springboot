package com.app.services.base;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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
  public List<E> findAll() throws Exception {

    try {
      List<E> entities = baseRepository.findAll();
      return entities;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  @Transactional
  public E findById(ID id) throws Exception {
    try {

      Optional<E> entityOptional = baseRepository.findById(id);

      if (!entityOptional.isPresent()) {
        throw new Exception("NOT_FOUND");
      }

      return entityOptional.get();

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  @Transactional
  public E save(E entity) throws Exception {
    try {
      E newEntity = baseRepository.save(entity);
      return newEntity;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  @Transactional
  public E update(ID id, E entity) throws Exception {
    try {

      Optional<E> optionalEntity = baseRepository.findById(id);

      if (!optionalEntity.isPresent()) {
        throw new Exception("NOT_FOUND");
      }

      E updateEntity = optionalEntity.get();
      updateEntity = baseRepository.save(entity);
      return updateEntity;

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
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
