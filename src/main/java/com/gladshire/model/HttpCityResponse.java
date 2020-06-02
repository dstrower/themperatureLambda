package com.gladshire.model;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpCityResponse {

  private String body;
  private String statusCode = "200";
  private Map<String, String> headers = new HashMap<>();

  public HttpCityResponse() {
    super();
    this.headers.put("Content-Type", "application/json");
  }

  public HttpCityResponse(List<String> cList) {
    this();
    Gson gson = new Gson();
    this.body = gson.toJson(cList);
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
