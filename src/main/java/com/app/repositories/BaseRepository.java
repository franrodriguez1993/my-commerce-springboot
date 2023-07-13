package com.app.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.BaseEntity;

public interface BaseRepository<E extends BaseEntity, ID extends Serializable> extends JpaRepository<E, ID> {

}
