package com.topica.demojspservlet.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topica.demojspservlet.model.Todo;
import com.topica.demojspservlet.service.TodoService;
import com.topica.demojspservlet.service.impl.TodoServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

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
        String address = "http://todos/api/todos";
        URL url = new URL(address);
        URLConnection conn = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
        int code = httpURLConnection.getResponseCode();
        if (code == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            JSONObject json = JSONObject.parseObject(result.toString());// 把String对象转为JSONObject对象
            List<Todo> todoList = new ArrayList<>();
            String version = json.getString("version");
            JSONArray jsonarray = json.getJSONArray("todoList");
            for (int i = 0; i < jsonarray.size(); i++) {
                Todo todo = new Todo();
                JSONObject jo = jsonarray.getJSONObject(i);
                todo.setId(jo.getLong("id"));
                todo.setName(jo.getString("name"));
                todo.setDescription(jo.getString("description"));
                todoList.add(todo);
            }
            req.setAttribute("todos", todoList);
            req.setAttribute("api_version", version);
        } else {
            req.setAttribute("todos", null);
            req.setAttribute("api_version", "");
        }
        if (code == 429) {
            req.setAttribute("errorMsg", "后端服务限流");
        }
        if (code == 503) {
            if (httpURLConnection.getHeaderField("x-envoy-overloaded") != null) {
                req.setAttribute("errorMsg", "后端服务断路");
            }
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}