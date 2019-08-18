package org.assignment.ex.web.server.server;

import com.sun.net.httpserver.HttpServer;
import org.assignment.ex.web.server.context.file.FileContext;
import org.assignment.ex.web.server.context.general.GeneralContext;
import org.assignment.ex.web.server.context.welcome.WelcomeContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

  private Logger log = LoggerFactory.getLogger(getClass());
  private HttpServer httpServer;

  public Server(InetSocketAddress inetSocketAddress, int backlog) {
    try {
      httpServer = HttpServer.create(inetSocketAddress, backlog);
      httpServer.createContext(FileContext.CONTEXT, new FileContext());
      httpServer.createContext(WelcomeContext.CONTEXT, new WelcomeContext());
      httpServer.createContext(GeneralContext.CONTEXT, new GeneralContext());
      httpServer.setExecutor(Executors.newCachedThreadPool());
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  /** Starts the web server. */
  public void start() {
    log.info("Starting the server...");
    httpServer.start();
  }
}
