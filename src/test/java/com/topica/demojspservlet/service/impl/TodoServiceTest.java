package com.topica.demojspservlet.service.impl;

import com.topica.demojspservlet.model.Todo;
import com.topica.demojspservlet.service.TodoService;
import java.util.List;
import org.junit.Test;

public class TodoServiceTest {
  TodoService todoService = new TodoServiceImpl();

  @Test
  public void testGetAll(){
    List<Todo> todoList = todoService.getAll();

    System.out.println(todoList.size());
  }

  @Test
  public void testGetOne(){
    Todo todo = todoService.getOne(Long.parseLong("3"));
    if (todo == null){
      System.out.println("null");
    } else{
      System.out.println(todo.getDescription());
    }
  }

  @Test
  public void testInsert(){
    Todo todo = new Todo();
    todo.setName("todo3");
    todo.setDescription("todo3 test");

    int rowCount = todoService.insert(todo);
    System.out.println(rowCount);
  }

  @Test
  public void testGetUpdate(){
    Todo todo = new Todo();
    todo.setId(Long.parseLong("3"));
    todo.setName("todo3");
    todo.setDescription("todo3 test update");

    int rowCount = todoService.update(todo);
    System.out.println(rowCount);
  }

  @Test
  public void testGetDelete(){
    int rowCount = todoService.delete(Long.parseLong("3"));
    System.out.println(rowCount);
  }



}
