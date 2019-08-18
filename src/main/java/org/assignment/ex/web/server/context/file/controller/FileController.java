package org.assignment.ex.web.server.context.file.controller;

import com.sun.net.httpserver.HttpExchange;
import org.apache.http.HttpStatus;
import org.assignment.ex.web.server.controller.UriController;
import org.assignment.ex.web.server.exception.BadRequestException;
import org.assignment.ex.web.server.resource.FileResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.assignment.ex.web.server.constant.HttpResponse.OK;

/** The rest controller for the file operations. */
public class FileController extends UriController {
  public static final String CONTEXT = "/file";
  private static final Object lock = new Object();
  private static final String FILE_URI = CONTEXT;
  private static final String QUERY_ID_FILE = "id";

  private static volatile FileController fileController;
  private final FileResourceService fileResourceService;
  private Logger log = LoggerFactory.getLogger(getClass());

  private FileController() {
    super();
    this.fileResourceService = FileResourceService.getInstance();
    registerUrisToHttpMethods();
  }

  public static FileController getInstance() {
    FileController tempFileController = fileController;

    if (tempFileController == null) {
      synchronized (lock) {
        tempFileController = fileController;
        if (tempFileController == null) {
          tempFileController = new FileController();
          fileController = tempFileController;
        }
      }
    }

    return tempFileController;
  }

  /**
   * Register the available methods to the URIs.
   */
  private void registerUrisToHttpMethods() {
    uriMapping.registerGet(FILE_URI, this::getFile);
    uriMapping.registerPost(FILE_URI, this::createFile);
    uriMapping.registerPut(FILE_URI, this::updateFile);
    uriMapping.registerDelete(FILE_URI, this::deleteFile);
  }

  /**
   * This method is register as GET for URI: {@value #FILE_URI}
   *
   * @param httpExchange
   */
  private void getFile(HttpExchange httpExchange) {
    log.info("GET received: {}", httpExchange.getRequestURI());
    String fileId = getQueryFileId(httpExchange);
    byte[] file = fileResourceService.getFile(fileId);
    respond(httpExchange, HttpStatus.SC_OK, file);
  }

  /**
   * This method is register as POST for URI: {@value #FILE_URI}
   *
   * @param httpExchange
   */
  private void createFile(HttpExchange httpExchange) {
    log.info("POST received: {}", httpExchange.getRequestURI());
    String fileId = getQueryFileId(httpExchange);
    fileResourceService.createFile(fileId, httpExchange.getRequestBody());
    respond(httpExchange, HttpStatus.SC_OK, OK.getBytes());
  }

  /**
   * This method is register as PUT for URI: {@value #FILE_URI}
   *
   * @param httpExchange
   */
  private void updateFile(HttpExchange httpExchange) {
    log.info("PUT received: {}", httpExchange.getRequestURI());
    String fileId = getQueryFileId(httpExchange);
    fileResourceService.updateFile(fileId, httpExchange.getRequestBody());
    respond(httpExchange, HttpStatus.SC_OK, OK.getBytes());
  }

  /**
   * This method is register as DELETE for URI: {@value #FILE_URI}
   *
   * @param httpExchange
   */
  private void deleteFile(HttpExchange httpExchange) {
    log.info("DELETE received: {}", httpExchange.getRequestURI());
    String fileId = getQueryFileId(httpExchange);
    fileResourceService.deleteFile(fileId);
    respond(httpExchange, HttpStatus.SC_OK, OK.getBytes());
  }

  private String getQueryFileId(HttpExchange httpExchange) {
    Map<String, List<String>> uriParameterMap = getUriParameters(httpExchange.getRequestURI());
    List<String> fileIds = uriParameterMap.get(QUERY_ID_FILE);

    if (fileIds == null || fileIds.size() != 1) {
      throw new BadRequestException();
    }
    // we only support one id in the request query
    return fileIds.get(0);
  }
}
