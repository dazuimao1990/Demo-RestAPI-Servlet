package com.topica.demojspservlet.repository;

import com.topica.demojspservlet.constants.TodoQuery;
import com.topica.demojspservlet.model.Todo;
import com.topica.demojspservlet.utils.connection.MySQLConnectionUtil;
import com.topica.demojspservlet.utils.memcached.MemcachedClientUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import net.spy.memcached.MemcachedClient;

public class TodoRepository {

  private MemcachedClient memcachedClient = MemcachedClientUtil.getMemcachedClient();

  public Todo getOne(Long id) {

    Todo todo = (Todo) memcachedClient.get("Todo" + id.toString());

    if (todo != null){
      return todo;
    }

    Connection connection = new MySQLConnectionUtil().getConnection();
    String query = TodoQuery.GET_BY_ID;

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);

      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        todo = new Todo();
        todo.setId(id);
        todo.setName(resultSet.getString("name"));
        todo.setDescription(resultSet.getString("description"));
        memcachedClient.set("Todo"+id.toString(), 3600, todo);

        return todo;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  public List<Todo> getAll() {
    Connection connection = new MySQLConnectionUtil().getConnection();

    String query = TodoQuery.GET_ALL;
    List<Todo> todoList = new ArrayList<>();

    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(query);

      int records = 1;
      while (resultSet.next()) {
        //Todo todo = resultSet.getObject(records, Todo.class);
        Todo todo = new Todo();
        todo.setId(resultSet.getLong("id"));
        todo.setName(resultSet.getString("name"));
        todo.setDescription(resultSet.getString("description"));
        todoList.add(todo);
        records++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return todoList;
  }

  public int insert(Todo object) {
    Connection connection = new MySQLConnectionUtil().getConnection();

    String query = TodoQuery.INSERT_ONE;

    int rowCount = 0;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, object.getName());
      preparedStatement.setString(2, object.getDescription());

      rowCount = preparedStatement.executeUpdate();
      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()){
        Long lastInsertedId = rs.getLong(1);
        object.setId(lastInsertedId);
        memcachedClient.set("Todo"+lastInsertedId.toString(), 3600, object);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return rowCount;
  }

  public int update(Todo object) {
    Connection connection = new MySQLConnectionUtil().getConnection();

    String query = TodoQuery.UPDATE_ONE;

    int rowCount = 0;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, object.getName());
      preparedStatement.setString(2, object.getDescription());
      preparedStatement.setLong(3, object.getId());

      rowCount = preparedStatement.executeUpdate();
      if (rowCount > 0){
        memcachedClient.set("Todo"+object.getId(), 3600, object);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return rowCount;
  }

  public int delete(Long id) {
    Connection connection = new MySQLConnectionUtil().getConnection();

    String query = TodoQuery.DELETE_ONE;

    Object oldTodo = memcachedClient.get("Todo"+id.toString());
    if (oldTodo != null){
      memcachedClient.delete("Todo"+id.toString());
    }

    int rowCount = 0;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setLong(1, id);

      rowCount = preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return rowCount;
  }
}
