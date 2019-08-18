package org.assignment.ex.web.server.service;

import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpExchangeService {

  private Logger log = LoggerFactory.getLogger(getClass());

  public void respond(HttpExchange httpExchange, int httpStatus, byte[] response) {
    try {
      httpExchange.sendResponseHeaders(httpStatus, response.length);
      httpExchange.getResponseBody().write(response);
      httpExchange.getResponseBody().flush();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }
}
