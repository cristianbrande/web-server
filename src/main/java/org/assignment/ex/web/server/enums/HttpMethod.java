package org.assignment.ex.web.server.enums;

import org.assignment.ex.web.server.exception.InvalidHttpMethodException;

import java.util.Arrays;

/**
 * Enum for all the HTTP methods.
 */
public enum HttpMethod {
  GET("GET"),
  HEAD("HEAD"),
  POST("POST"),
  PUT("PUT"),
  DELETE("DELETE"),
  CONNECT("CONNECT"),
  OPTIONS("OPTIONS"),
  TRACE("TRACE"),
  PATCH("PATCH");

  private final String method;

  HttpMethod(String method) {
    this.method = method;
  }

  public static HttpMethod fromValue(String method) {
    return Arrays.stream(values())
        .filter(value -> value.getMethod().equals(method))
        .findFirst()
        .orElseThrow(() -> new InvalidHttpMethodException(method));
  }

  public String getMethod() {
    return method;
  }

  @Override
  public String toString() {
    return "HttpMethod{" + "method='" + method + '\'' + '}';
  }
}
