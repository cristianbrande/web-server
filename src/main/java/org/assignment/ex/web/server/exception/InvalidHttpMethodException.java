package org.assignment.ex.web.server.exception;

/**
 * Exception used when the request method received is not one of the supported ones:
 * @see org.assignment.ex.web.server.enums.HttpMethod
 *
 */
public class InvalidHttpMethodException extends RuntimeException {
  private static final long serialVersionUID = 7861078743206357117L;
  private static final String message = "Invalid http method encountered: ";

  public InvalidHttpMethodException(String method) {
    super(message + method);
  }
}
