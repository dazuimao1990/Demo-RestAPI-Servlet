package com.topica.demojspservlet.constants;

public interface TodoQuery {
  static final String GET_ALL = "SELECT t.id, t.name, t.description FROM todo t";
  static final String GET_BY_ID = "SELECT t.id, t.name, t.description FROM todo t WHERE t.id = ?";
  static final String INSERT_ONE = "INSERT INTO todo (name, description) VALUES (?, ?)";
  static final String DELETE_ONE = "DELETE FROM todo WHERE id = ?";
  static final String UPDATE_ONE = "UPDATE todo t SET t.name = ?, t.description = ? WHERE t.id = ?";
}
