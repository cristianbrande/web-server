package org.assignment.ex.web.server.controller;

import com.sun.net.httpserver.HttpExchange;
import org.assignment.ex.web.server.enums.HttpMethod;
import org.assignment.ex.web.server.exception.UnsupportedHttpMethodException;

/** Default mechanism for all the HTTP methods. */
public abstract class Controller {

  public void get(HttpExchange httpExchange) {
    throw new UnsupportedHttpMethodException(HttpMethod.GET);
  }

  public void head(HttpExchange httpExchange) {
    throw new UnsupportedHttpMethodException(HttpMethod.HEAD);
  }

  public void post(HttpExchange httpExchange) {
    throw new UnsupportedHttpMethodException(HttpMethod.POST);
  }

  public void put(HttpExchange httpExchange) {
    throw new UnsupportedHttpMethodException(HttpMethod.PUT);
  }

  public void delete(HttpExchange httpExchange) {
    throw new UnsupportedHttpMethodException(HttpMethod.DELETE);
  }

  public void connect(HttpExchange httpExchange) {
    throw new UnsupportedHttpMethodException(HttpMethod.CONNECT);
  }

  public void options(HttpExchange httpExchange) {
    throw new UnsupportedHttpMethodException(HttpMethod.OPTIONS);
  }

  public void trace(HttpExchange httpExchange) {
    throw new UnsupportedHttpMethodException(HttpMethod.TRACE);
  }

  public void patch(HttpExchange httpExchange) {
    throw new UnsupportedHttpMethodException(HttpMethod.PATCH);
  }
}
