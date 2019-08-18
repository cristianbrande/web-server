package org.assignment.ex.web.server;

import org.assignment.ex.web.server.config.Configuration;
import org.assignment.ex.web.server.server.Server;

import java.net.InetSocketAddress;

import static org.assignment.ex.web.server.config.Configuration.BACKLOG;
import static org.assignment.ex.web.server.config.Configuration.PORT;

public class ApplicationMain {

  public static void main(String[] args) {

    Configuration configuration = Configuration.getInstance();
    int backLog = Integer.parseInt(configuration.getProperty(BACKLOG));
    int port = Integer.parseInt(configuration.getProperty(PORT));

    Server server = new Server(new InetSocketAddress(port), backLog);
    server.start();
  }
}
