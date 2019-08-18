package org.assignment.ex.web.server.context;

import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class UriMapping {

  private Map<String, Consumer<HttpExchange>> uriToGetMethodMapping;
  private Map<String, Consumer<HttpExchange>> uriToHeadMethodMapping;
  private Map<String, Consumer<HttpExchange>> uriToPostMethodMapping;
  private Map<String, Consumer<HttpExchange>> uriToPutMethodMapping;
  private Map<String, Consumer<HttpExchange>> uriToDeleteMethodMapping;
  private Map<String, Consumer<HttpExchange>> uriToConnectMethodMapping;
  private Map<String, Consumer<HttpExchange>> uriToOptionsMethodMapping;
  private Map<String, Consumer<HttpExchange>> uriToTraceMethodMapping;
  private Map<String, Consumer<HttpExchange>> uriToPatchMethodMapping;

  public UriMapping() {
    this.uriToGetMethodMapping = new HashMap<>();
    this.uriToHeadMethodMapping = new HashMap<>();
    this.uriToPostMethodMapping = new HashMap<>();
    this.uriToPutMethodMapping = new HashMap<>();
    this.uriToDeleteMethodMapping = new HashMap<>();
    this.uriToConnectMethodMapping = new HashMap<>();
    this.uriToOptionsMethodMapping = new HashMap<>();
    this.uriToTraceMethodMapping = new HashMap<>();
    this.uriToPatchMethodMapping = new HashMap<>();
  }

  public void registerGet(String uri, Consumer<HttpExchange> method) {
    this.uriToGetMethodMapping.put(uri, method);
  }

  public void registerHead(String uri, Consumer<HttpExchange> method) {
    this.uriToHeadMethodMapping.put(uri, method);
  }

  public void registerPost(String uri, Consumer<HttpExchange> method) {
    this.uriToPostMethodMapping.put(uri, method);
  }

  public void registerPut(String uri, Consumer<HttpExchange> method) {
    this.uriToPutMethodMapping.put(uri, method);
  }

  public void registerDelete(String uri, Consumer<HttpExchange> method) {
    this.uriToDeleteMethodMapping.put(uri, method);
  }

  public void registerConnect(String uri, Consumer<HttpExchange> method) {
    this.uriToConnectMethodMapping.put(uri, method);
  }

  public void registerOptions(String uri, Consumer<HttpExchange> method) {
    this.uriToOptionsMethodMapping.put(uri, method);
  }

  public void registerTrace(String uri, Consumer<HttpExchange> method) {
    this.uriToTraceMethodMapping.put(uri, method);
  }

  public void registerPatch(String uri, Consumer<HttpExchange> method) {
    this.uriToPatchMethodMapping.put(uri, method);
  }

  public Consumer<HttpExchange> getMethod(String uri) {
    return uriToGetMethodMapping.get(uri);
  }

  public Consumer<HttpExchange> headMethod(String uri) {
    return uriToHeadMethodMapping.get(uri);
  }

  public Consumer<HttpExchange> postMethod(String uri) {
    return uriToPostMethodMapping.get(uri);
  }

  public Consumer<HttpExchange> putMethod(String uri) {
    return uriToPutMethodMapping.get(uri);
  }

  public Consumer<HttpExchange> deleteMethod(String uri) {
    return uriToDeleteMethodMapping.get(uri);
  }

  public Consumer<HttpExchange> connectMethod(String uri) {
    return uriToConnectMethodMapping.get(uri);
  }

  public Consumer<HttpExchange> optionsMethod(String uri) {
    return uriToOptionsMethodMapping.get(uri);
  }

  public Consumer<HttpExchange> traceMethod(String uri) {
    return uriToTraceMethodMapping.get(uri);
  }

  public Consumer<HttpExchange> patchMethod(String uri) {
    return uriToPatchMethodMapping.get(uri);
  }
}
