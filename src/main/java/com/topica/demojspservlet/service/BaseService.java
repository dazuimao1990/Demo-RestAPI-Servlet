package com.topica.demojspservlet.service;

import java.util.List;

public interface BaseService<T, ID> {
  public T getOne(ID id);

  public List<T> getAll();

  public int insert(T object);

  public int update(T object);

  public int delete(ID id);
}
