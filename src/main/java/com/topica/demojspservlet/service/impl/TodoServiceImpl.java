package com.topica.demojspservlet.service.impl;

import com.topica.demojspservlet.model.Todo;
import com.topica.demojspservlet.repository.TodoRepository;
import com.topica.demojspservlet.service.TodoService;
import java.util.List;

public class TodoServiceImpl implements TodoService {

  private TodoRepository todoRepository;

  public TodoServiceImpl(){
    super();
    this.todoRepository = new TodoRepository();
  }

  @Override
  public Todo getOne(Long id) {
    return todoRepository.getOne(id);
  }

  @Override
  public List<Todo> getAll() {
    return todoRepository.getAll();
  }

  @Override
  public int insert(Todo object) {
    return todoRepository.insert(object);
  }

  @Override
  public int update(Todo object) {
    return todoRepository.update(object);
  }

  @Override
  public int delete(Long id) {
    return todoRepository.delete(id);
  }
}
