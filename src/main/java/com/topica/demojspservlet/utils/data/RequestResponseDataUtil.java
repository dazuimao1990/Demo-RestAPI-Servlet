package com.topica.demojspservlet.utils.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestResponseDataUtil {

  static String getRequest(HttpServletRequest req) throws IOException {
//    StringBuilder buffer = new StringBuilder();
//    BufferedReader reader = request.getReader();
//    String line;
//    while ((line = reader.readLine()) != null) {
//      buffer.append(line);
//    }
//    String data = buffer.toString()

    String body = req.getReader().lines()
        .reduce(System.lineSeparator(), (accumulator, actual) -> accumulator + actual);//String::concat);

    return body;
  }

  static void sendAsJson(HttpServletResponse resp, Object obj) throws IOException {
    PrintWriter out = resp.getWriter();

    resp.setContentType("application/json");
    resp.setCharacterEncoding("utf-8");

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonObjString = objectMapper.writeValueAsString(obj);

    out.print(jsonObjString);
    out.flush();
  }
}
