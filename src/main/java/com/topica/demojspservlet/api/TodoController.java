package com.topica.demojspservlet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topica.demojspservlet.model.Todo;
import com.topica.demojspservlet.service.TodoService;
import com.topica.demojspservlet.service.impl.TodoServiceImpl;
import com.topica.demojspservlet.utils.data.RequestResponseDataUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;

@WebServlet(name = "TodoController", urlPatterns = { "/api/todos/*" })
public class TodoController extends HttpServlet {

  private TodoService todoService = new TodoServiceImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("utf-8");
    String pathInfo = req.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) { // GET /api/todos
      List<Todo> todoList = todoService.getAll();
      APIResult re = new APIResult();
      re.todoList = todoList;
      re.total = todoList.size();
      re.message = "success get todo lists";
      RequestResponseDataUtil.sendAsJson(resp, re);
      System.out.println("success get todo lists");
      // log.info("get all todo size: ", todoList.size());
      return;
    }

    String[] splits = pathInfo.split("/"); // GET /api/todos/{id}
    if (splits.length != 2) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    String todoId = splits[1];
    boolean isId = todoId.chars().allMatch(Character::isDigit);

    if (isId) {
      Todo todo = todoService.getOne(Long.parseLong(todoId));
      if (todo == null) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        // log.info("not found: " + todoId);

      } else {
        // log.info("found: " + todoId);
        RequestResponseDataUtil.sendAsJson(resp, todo);
      }
    } else {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    req.setCharacterEncoding("utf-8");
    String pathInfo = req.getPathInfo();
    ObjectMapper mapper = new ObjectMapper();
    if (pathInfo == null || pathInfo.equals("/")) { // POST /api/todos
      String body = RequestResponseDataUtil.getRequest(req);
      Todo todo = mapper.readValue(body, Todo.class);
      int rowCount = todoService.insert(todo);
      if (rowCount == 0) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      } else {
        resp.sendError(HttpServletResponse.SC_OK);
      }
      return;
    } else {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    req.setCharacterEncoding("utf-8");
    String pathInfo = req.getPathInfo();
    ObjectMapper mapper = new ObjectMapper();
    if (pathInfo == null || pathInfo.equals("/")) { // PUT /api/todos
      String body = RequestResponseDataUtil.getRequest(req);
      Todo todo = mapper.readValue(body, Todo.class);
      int rowCount = todoService.update(todo);
      if (rowCount == 0) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      } else {
        resp.sendError(HttpServletResponse.SC_OK);
      }
      return;
    } else {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    req.setCharacterEncoding("utf-8");
    String pathInfo = req.getPathInfo();

    if (pathInfo == null || pathInfo.equals("/")) { // DELETE /api/todos
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    String[] splits = pathInfo.split("/"); // DELETE /api/todos/{id}
    if (splits.length != 2) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    String todoId = splits[1];
    boolean isId = todoId.chars().allMatch(Character::isDigit);

    if (isId) {
      int rowCount = todoService.delete(Long.parseLong(todoId));
      if (rowCount == 0) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      } else {
        resp.sendError(HttpServletResponse.SC_OK);
      }
    } else {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}
