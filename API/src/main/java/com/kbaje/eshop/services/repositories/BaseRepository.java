package com.kbaje.eshop.services.repositories;

import java.util.UUID;

import com.kbaje.eshop.models.BaseEntity;

import org.springframework.data.repository.CrudRepository;

public interface BaseRepository<TEntity extends BaseEntity> extends CrudRepository<TEntity, UUID> {
    
}
