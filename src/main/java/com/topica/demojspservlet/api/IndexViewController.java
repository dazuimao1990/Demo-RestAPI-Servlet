package com.topica.demojspservlet.api;

import com.topica.demojspservlet.model.Todo;
import com.topica.demojspservlet.service.TodoService;
import com.topica.demojspservlet.service.impl.TodoServiceImpl;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/" })
public class IndexViewController extends HttpServlet {

    TodoService todoService = new TodoServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Todo> list = todoService.getAll();
        System.out.println(Arrays.deepToString(list.toArray()));
        req.setAttribute("todos", list);
        req.getRequestDispatcher("TodoCRUD.jsp").forward(req, resp);
    }
}