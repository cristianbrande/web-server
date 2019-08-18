package org.assignment.ex.web.server.context.file;

import com.sun.net.httpserver.HttpExchange;
import org.assignment.ex.web.server.context.Context;
import org.assignment.ex.web.server.context.file.controller.FileController;

/**
 * This class handles all the request received on {@value #CONTEXT}.
 */
public class FileContext extends Context<FileController> {

  public static final String CONTEXT = FileController.CONTEXT;

  @Override
  public void handle(HttpExchange httpExchange) {
    handleRequest(httpExchange, FileController.getInstance());
  }
}
