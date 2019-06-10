package com.topica.demojspservlet.api;

import java.util.List;

import com.topica.demojspservlet.model.Todo;

public class APIResult extends Object {
    public List<Todo> todoList;
    public String message;
    public int total;
}