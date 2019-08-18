package org.assignment.ex.web.server.context.general;

import com.sun.net.httpserver.HttpExchange;
import org.apache.http.HttpStatus;
import org.assignment.ex.web.server.context.Context;

import static org.assignment.ex.web.server.constant.HttpResponse.NOT_FOUND;

/** The context is used to catch all unmapped uris and to return HttpStatus.SC_NOT_FOUND */
public class GeneralContext extends Context {

  public static final String CONTEXT = "/";

  @Override
  public void handle(HttpExchange httpExchange) {
    respond(httpExchange, HttpStatus.SC_NOT_FOUND, NOT_FOUND.getBytes());
  }
}
