package com.topica.demojspservlet.utils.data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public interface RequestResponseDataUtil {

  static String getRequest(HttpServletRequest req) throws IOException {
    // StringBuilder buffer = new StringBuilder();
    // BufferedReader reader = request.getReader();
    // String line;
    // while ((line = reader.readLine()) != null) {
    // buffer.append(line);
    // }
    // String data = buffer.toString()

    String body = req.getReader().lines().reduce(System.lineSeparator(), (accumulator, actual) -> accumulator + actual);// String::concat);

    return body;
  }

  static void sendAsJson(HttpServletResponse resp, Object obj) throws IOException {
    resp.setContentType("application/json; charset=UTF-8");
    resp.setCharacterEncoding("UTF-8");
    PrintWriter out = resp.getWriter();
    out.print(JSON.toJSONString(obj));
    out.flush();
  }
}
