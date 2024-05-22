package me.dio.service;

import java.util.List;

public interface CrudService<ID, T> {
    T findById(ID id);

    List<T> findAll();

    T create(T entity);

    T update(ID id, T entity);

    void delete(ID id);
}
