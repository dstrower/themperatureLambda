package com.gladshire.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.client.builder.AwsSyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.gladshire.model.TemperatureRequest;

public class GetTemperatureClient implements RequestHandler<TemperatureRequest, String> {

  @Override
  public String handleRequest(TemperatureRequest request, Context context) {

    String response = "hello";
    context.getLogger().log("Input: " + request);
    if (request != null) {
      String id = request.getId();
      context.getLogger().log("Found the id : " + id);
      response = response + ": " + id;
      AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
      DynamoDB dynamoDB = new DynamoDB(client);

      // Get a reference to the Widget table
      Table table = dynamoDB.getTable("weather");

      // Get our item by ID
      Item item = table.getItem("sc", id);
      if (item != null) {
        String temp = item.getString("t");
        response = response + " is: " + temp;
      }
    }

    return response;
  }
}
