package com.topica.demojspservlet.repository;

import com.topica.demojspservlet.constants.TodoQuery;
import com.topica.demojspservlet.model.Todo;
import com.topica.demojspservlet.utils.connection.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TodoRepository {

  public Todo getOne(Long id) {

    Connection connection = new MySQLConnectionUtil().getConnection();
    String query = TodoQuery.GET_BY_ID;

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);

      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setName(resultSet.getString("name"));
        todo.setDescription(resultSet.getString("description"));

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
      while (resultSet.next()) {
        // Todo todo = resultSet.getObject(records, Todo.class);
        Todo todo = new Todo();
        todo.setId(resultSet.getLong("id"));
        todo.setName(resultSet.getString("name"));
        todo.setDescription(resultSet.getString("description"));
        todoList.add(todo);
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

    String insertquery = TodoQuery.INSERT_ONE;

    int rowCount = 0;
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(insertquery, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, object.getName());
      preparedStatement.setString(2, object.getDescription());
      rowCount = preparedStatement.executeUpdate();
      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        Long lastInsertedId = rs.getLong(1);
        object.setId(lastInsertedId);
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
      if (rowCount > 0) {

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
