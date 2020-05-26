package com.gladshire.lambda;

import com.gladshire.model.Temperature;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class HttpTemperatureResponse {

  private String body;
  private String statusCode = "200";
  private Map<String, String> headers = new HashMap<>();

  public HttpTemperatureResponse() {
    super();
    this.headers.put("Content-Type", "application/json");
  }

  public HttpTemperatureResponse(Temperature temperature) {
    this();
    Gson gson = new Gson();
    this.body = gson.toJson(temperature);
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
