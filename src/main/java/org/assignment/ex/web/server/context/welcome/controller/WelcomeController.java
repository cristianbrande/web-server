package org.assignment.ex.web.server.context.welcome.controller;

import com.sun.net.httpserver.HttpExchange;
import org.apache.http.HttpStatus;
import org.assignment.ex.web.server.controller.UriController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WelcomeController extends UriController {
  public static final String CONTEXT = "/welcome";
  private static final String URI = CONTEXT;
  private static final Object lock = new Object();

  private static WelcomeController welcomeController;
  private Logger log = LoggerFactory.getLogger(getClass());

  private WelcomeController() {
    super();
    registerUrisToHttpMethods();
  }

  public static WelcomeController getInstance() {
    WelcomeController tempWelcomeController = welcomeController;

    if (tempWelcomeController == null) {
      synchronized (lock) {
        tempWelcomeController = welcomeController;
        if (tempWelcomeController == null) {
          tempWelcomeController = new WelcomeController();
          welcomeController = tempWelcomeController;
        }
      }
    }

    return tempWelcomeController;
  }

  private void registerUrisToHttpMethods() {
    uriMapping.registerGet(URI, this::getWelcome);
  }

  /**
   * This method is register as GET for URI: {@value #URI}
   *
   * @param httpExchange
   */
  private void getWelcome(HttpExchange httpExchange) {
    log.info("Welcome httpExchange received: {}", httpExchange);
    respond(httpExchange, HttpStatus.SC_OK, "Welcome".getBytes());
  }
}
