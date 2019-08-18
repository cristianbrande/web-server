package org.assignment.ex.web.server.context;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.http.HttpStatus;
import org.assignment.ex.web.server.controller.UriController;
import org.assignment.ex.web.server.enums.HttpMethod;
import org.assignment.ex.web.server.exception.InvalidHttpMethodException;
import org.assignment.ex.web.server.exception.UnsupportedHttpMethodException;
import org.assignment.ex.web.server.service.HttpExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assignment.ex.web.server.constant.HttpResponse.INTERNAL_SERVER_ERROR;
import static org.assignment.ex.web.server.constant.HttpResponse.NOT_FOUND;

/**
 * The context handles any request received on the server and redirects it
 * to the given rest controller, on the right http method.
 * @param <T>
 */
public abstract class Context<T extends UriController> implements HttpHandler {
  private Logger log = LoggerFactory.getLogger(getClass());
  private HttpExchangeService httpExchangeService = new HttpExchangeService();

  protected void handleRequest(HttpExchange httpExchange, T t) {
    String requestMethod = httpExchange.getRequestMethod();

    try {
      HttpMethod httpMethod = HttpMethod.fromValue(requestMethod);
      handleRequestMethod(t, httpExchange, httpMethod);
    } catch (InvalidHttpMethodException e) {
      log.error(e.getMessage());
      respond(httpExchange, HttpStatus.SC_BAD_REQUEST, e.getMessage().getBytes());
    } catch (UnsupportedHttpMethodException e) {
      log.error(e.getMessage() + " for uri: " + httpExchange.getRequestURI());
      respond(httpExchange, HttpStatus.SC_NOT_FOUND, NOT_FOUND.getBytes());
    } catch (RuntimeException e) {
      log.error(e.getMessage(), e);
      respond(httpExchange, HttpStatus.SC_INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getBytes());
    }
  }

  private void handleRequestMethod(T t, HttpExchange httpExchange, HttpMethod httpMethod) {
    switch (httpMethod) {
      case GET:
        t.get(httpExchange);
        break;
      case PUT:
        t.put(httpExchange);
        break;
      case POST:
        t.post(httpExchange);
        break;
      case DELETE:
        t.delete(httpExchange);
        break;
      case HEAD:
        t.head(httpExchange);
        break;
      case PATCH:
        t.patch(httpExchange);
        break;
      case TRACE:
        t.trace(httpExchange);
        break;
      case CONNECT:
        t.connect(httpExchange);
        break;
      case OPTIONS:
        t.options(httpExchange);
        break;
      default:
        throw new UnsupportedHttpMethodException(httpMethod);
    }
  }

  /**
   * Writes the given status and response on the httpExchange.
   * @param httpExchange
   * @param httpStatus
   * @param response
   */
  protected void respond(HttpExchange httpExchange, int httpStatus, byte[] response) {
    httpExchangeService.respond(httpExchange, httpStatus, response);
  }
}
