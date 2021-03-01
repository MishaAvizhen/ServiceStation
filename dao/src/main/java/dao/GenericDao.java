package dao;

import entity.BaseEntity;

import java.util.List;


public interface GenericDao<E extends BaseEntity> {
    List<E> findAll();
    E findById(Long id);
    void deleteById(Long id);
    E update(E entity);
    E save(E entity);
}
