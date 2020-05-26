package com.gladshire.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.gladshire.model.Temperature;

public class GetTemperatureClient
    implements RequestHandler<HttpQuerystringRequest, HttpTemperatureResponse> {

  @Override
  public HttpTemperatureResponse handleRequest(HttpQuerystringRequest request, Context context) {

    HttpTemperatureResponse response = new HttpTemperatureResponse();
    context.getLogger().log("Input: " + request);
    if (request != null) {
      String id = (String) request.getQueryStringParameters().get("id");
      context.getLogger().log("Found the id : " + id);

      AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
      DynamoDB dynamoDB = new DynamoDB(client);

      // Get a reference to the Widget table
      Table table = dynamoDB.getTable("weather");

      // Get our item by ID
      Item item = table.getItem("sc", id);

      if (item != null) {
        String temp = item.getString("t");
        Temperature temperature = new Temperature();
        temperature.setId(id);
        temperature.setTemp(temp);
        return new HttpTemperatureResponse(temperature);
      } else {
    	  response.setStatusCode("404");
      }
    }

    return response;
  }
}
