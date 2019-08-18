package org.assignment.ex.web.server.exception;

/**
 * The exception is thrown when the file path of the file received from
 * the request, once evaluated to its full path is out of the designated
 * root directory. (For example if the filename is of such: /../../filename)
 */
public class FileLocationOutOfRepoException extends RuntimeException{
    private static final long serialVersionUID = 6927079280295511627L;
}
