package org.assignment.ex.web.server.context.welcome;

import com.sun.net.httpserver.HttpExchange;
import org.assignment.ex.web.server.context.Context;
import org.assignment.ex.web.server.context.welcome.controller.WelcomeController;

/**
 * The context is used to display the welcome page. Only the GET request method displays the page.
 */
public class WelcomeContext extends Context<WelcomeController> {

  public static final String CONTEXT = WelcomeController.CONTEXT;

  @Override
  public void handle(HttpExchange httpExchange) {
    handleRequest(httpExchange, WelcomeController.getInstance());
  }
}
