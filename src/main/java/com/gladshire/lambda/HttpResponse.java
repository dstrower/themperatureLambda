package com.gladshire.lambda;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

  private String body;
  private String statusCode = "200";
  private Map<String, String> headers = new HashMap<>();

  public HttpResponse() {
    super();
    this.headers.put("Content-Type", "application/json");
  }

  

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }
}
