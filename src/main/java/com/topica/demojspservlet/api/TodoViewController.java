package com.topica.demojspservlet.api;

import com.topica.demojspservlet.model.Todo;
import com.topica.demojspservlet.service.TodoService;
import com.topica.demojspservlet.service.impl.TodoServiceImpl;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/todo" })
public class TodoViewController extends HttpServlet {

  TodoService todoService = new TodoServiceImpl();

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    req.setCharacterEncoding("utf-8");
    resp.setCharacterEncoding("utf-8");
    String name = req.getParameter("name");
    String description = req.getParameter("description");

    Todo todo = new Todo();

    if (name != null) {
      todo.setName(name);
    }
    if (description != null) {
      todo.setDescription(description);
    } else {
      req.setAttribute("errors", "description can not be empty");
      List<Todo> list = todoService.getAll();
      req.setAttribute("todos", list);
      req.getRequestDispatcher("TodoCRUD.jsp").forward(req, resp);
      return;
    }

    if (req.getParameter("btnInsert") != null) {
      todoService.insert(todo);
      req.setAttribute("errors", "Inserted Successfully");
    } else if (req.getParameter("btnUpdate") != null) {
      if (todo.getId() != null) {
        todoService.update(todo);
        req.setAttribute("errors", "Updated Successfully");
      }
    } else if (req.getParameter("btnDelete") != null) {
      if (todo.getId() != null) {
        todoService.delete(todo.getId());
        req.setAttribute("errors", "Deleted Successfully");
      }
    }
    List<Todo> list = todoService.getAll();
    req.setAttribute("todos", list);
    req.getRequestDispatcher("TodoCRUD.jsp").forward(req, resp);
  }
}
