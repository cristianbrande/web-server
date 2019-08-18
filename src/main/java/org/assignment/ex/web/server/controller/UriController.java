package org.assignment.ex.web.server.controller;

import com.sun.net.httpserver.HttpExchange;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.assignment.ex.web.server.context.UriMapping;
import org.assignment.ex.web.server.exception.BadRequestException;
import org.assignment.ex.web.server.exception.FileLocationOutOfRepoException;
import org.assignment.ex.web.server.exception.FileNotFoundException;
import org.assignment.ex.web.server.service.HttpExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.assignment.ex.web.server.constant.HttpResponse.*;

/** The controller accepts the right method to the wright URI. */
public class UriController extends Controller {

  protected UriMapping uriMapping;
  private Logger log = LoggerFactory.getLogger(getClass());
  private HttpExchangeService httpExchangeService;

  protected UriController() {
    this.uriMapping = new UriMapping();
    this.httpExchangeService = new HttpExchangeService();
  }

  /**
   * If there is a registered method for the URI call it, otherwise call the default mechanism found in
   * @see org.assignment.ex.web.server.controller.Controller#get(HttpExchange)
   */
  @Override
  public void get(HttpExchange httpExchange) {
    Consumer<HttpExchange> method = uriMapping.getMethod(httpExchange.getRequestURI().getPath());

    if (method != null) {
      accept(method, httpExchange);
    } else {
      super.get(httpExchange);
    }
  }

  /**
   * If there is a registered method for the URI call it, otherwise call the default mechanism found in
   * @see org.assignment.ex.web.server.controller.Controller#head(HttpExchange)
   */
  @Override
  public void head(HttpExchange httpExchange) {
    Consumer<HttpExchange> method = uriMapping.headMethod(httpExchange.getRequestURI().getPath());

    if (method != null) {
      accept(method, httpExchange);
    } else {
      super.head(httpExchange);
    }
  }

  /**
   * If there is a registered method for the URI call it, otherwise call the default mechanism found in
   * @see org.assignment.ex.web.server.controller.Controller#post(HttpExchange)
   */
  @Override
  public void post(HttpExchange httpExchange) {
    Consumer<HttpExchange> method = uriMapping.postMethod(httpExchange.getRequestURI().getPath());

    if (method != null) {
      accept(method, httpExchange);
    } else {
      super.post(httpExchange);
    }
  }

  /**
   * If there is a registered method for the URI call it, otherwise call the default mechanism found in
   * @see org.assignment.ex.web.server.controller.Controller#put(HttpExchange)
   */
  @Override
  public void put(HttpExchange httpExchange) {
    Consumer<HttpExchange> method = uriMapping.putMethod(httpExchange.getRequestURI().getPath());

    if (method != null) {
      accept(method, httpExchange);
    } else {
      super.put(httpExchange);
    }
  }

  /**
   * If there is a registered method for the URI call it, otherwise call the default mechanism found in
   * @see org.assignment.ex.web.server.controller.Controller#delete(HttpExchange)
   */
  @Override
  public void delete(HttpExchange httpExchange) {
    Consumer<HttpExchange> method = uriMapping.deleteMethod(httpExchange.getRequestURI().getPath());

    if (method != null) {
      accept(method, httpExchange);
    } else {
      super.delete(httpExchange);
    }
  }

  /**
   * If there is a registered method for the URI call it, otherwise call the default mechanism found in
   * @see org.assignment.ex.web.server.controller.Controller#connect(HttpExchange)
   */
  @Override
  public void connect(HttpExchange httpExchange) {
    Consumer<HttpExchange> method =
        uriMapping.connectMethod(httpExchange.getRequestURI().getPath());

    if (method != null) {
      accept(method, httpExchange);
    } else {
      super.connect(httpExchange);
    }
  }

  /**
   * If there is a registered method for the URI call it, otherwise call the default mechanism found in
   * @see org.assignment.ex.web.server.controller.Controller#options(HttpExchange)
   */
  @Override
  public void options(HttpExchange httpExchange) {
    Consumer<HttpExchange> method =
        uriMapping.optionsMethod(httpExchange.getRequestURI().getPath());

    if (method != null) {
      accept(method, httpExchange);
    } else {
      super.options(httpExchange);
    }
  }

  /**
   * If there is a registered method for the URI call it, otherwise call the default mechanism found in
   * @see org.assignment.ex.web.server.controller.Controller#trace(HttpExchange)
   */
  @Override
  public void trace(HttpExchange httpExchange) {
    Consumer<HttpExchange> method = uriMapping.traceMethod(httpExchange.getRequestURI().getPath());

    if (method != null) {
      accept(method, httpExchange);
    } else {
      super.trace(httpExchange);
    }
  }

  /**
   * If there is a registered method for the URI call it, otherwise call the default mechanism found in
   * @see org.assignment.ex.web.server.controller.Controller#patch(HttpExchange)
   */
  @Override
  public void patch(HttpExchange httpExchange) {
    Consumer<HttpExchange> method = uriMapping.patchMethod(httpExchange.getRequestURI().getPath());

    if (method != null) {
      accept(method, httpExchange);
    } else {
      super.patch(httpExchange);
    }
  }

  /**
   * Retrieves the uri params found on the request URI.
   * @param uri
   * @return
   */
  protected Map<String, List<String>> getUriParameters(URI uri) {
    Map<String, List<String>> parameterMap = new HashMap<>();
    List<NameValuePair> parameters = URLEncodedUtils.parse(uri, Charset.forName("UTF-8"));

    for (NameValuePair parameter : parameters) {
      List<String> paramValues = parameterMap.get(parameter.getName());
      if (paramValues == null) {
        paramValues = new ArrayList<>();
      }
      paramValues.add(parameter.getValue());
      parameterMap.put(parameter.getName(), paramValues);
    }

    return parameterMap;
  }

  /**
   * Invokes the method with the httpExchange argument
   * @param method
   * @param httpExchange
   */
  private void accept(Consumer<HttpExchange> method, HttpExchange httpExchange) {
    try {
      method.accept(httpExchange);
    } catch (FileNotFoundException e) {
      log.error(e.getMessage(), e);
      respond(httpExchange, HttpStatus.SC_NOT_FOUND, NOT_FOUND.getBytes());
    } catch (BadRequestException | FileLocationOutOfRepoException e) {
      log.error(e.getMessage(), e);
      respond(httpExchange, HttpStatus.SC_BAD_REQUEST, BAD_REQUEST.getBytes());
    } catch (RuntimeException e) {
      log.error(e.getMessage(), e);
      respond(httpExchange, HttpStatus.SC_INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getBytes());
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
