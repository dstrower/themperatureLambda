package com.gladshire.lambda;

import java.util.Map;

public class HttpQuerystringRequest {
  Map<String, String> queryStringParameters;

  public Map<String, String> getQueryStringParameters() {
    return queryStringParameters;
  }

  public void setQueryStringParameters(Map<String, String> queryStringParameters) {
    this.queryStringParameters = queryStringParameters;
  }
}