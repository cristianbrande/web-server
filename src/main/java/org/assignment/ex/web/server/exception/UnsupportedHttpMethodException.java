package org.assignment.ex.web.server.exception;

import org.assignment.ex.web.server.enums.HttpMethod;

/** Default mechanism for all the http method verbs on each uri is to throw this exception. */
public class UnsupportedHttpMethodException extends RuntimeException {
  private static final long serialVersionUID = -5445493050775529234L;
  private static final String message = "Unsupported http method encountered: ";

  public UnsupportedHttpMethodException(HttpMethod httpMethod) {
    super(message + httpMethod.getMethod());
  }
}
