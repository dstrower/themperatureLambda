package com.gladshire.lambda;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.gladshire.model.HttpRequest;
import com.gladshire.model.HttpResponse;
import com.gladshire.model.Temperature;
import com.google.gson.Gson;

public class UpdateTemperatureClient implements RequestHandler<HttpRequest, HttpResponse> {

	@Override
	public HttpResponse handleRequest(HttpRequest input, Context context) {

		HttpResponse response = new HttpResponse();
		context.getLogger().log("Input: " + input);
		String body = input.getBody();
		context.getLogger().log("body: " + body);
		Gson gson = new Gson();
		Temperature temperature = gson.fromJson(body, Temperature.class);
		if (temperature != null) {
			String id = temperature.getId();
			context.getLogger().log("Found the id : " + id);

			AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
			DynamoDB dynamoDB = new DynamoDB(client);

			// Get a reference to the Widget table
			Table table = dynamoDB.getTable("weather");

			// Get our item by ID
			Item item = table.getItem("sc", id);

			if (item != null) {
				// update the table
				context.getLogger()
						.log("Updating the table with id: " + id + " and temperature: " + temperature.getTemp());
				UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("sc", id)
						.withUpdateExpression("set t = :t")
						.withValueMap(new ValueMap().withString(":t", temperature.getTemp()))
						.withReturnValues(ReturnValue.UPDATED_NEW);
				try {
					context.getLogger().log("Updating the item...");
					UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
					context.getLogger().log("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

				} catch (Exception e) {
					context.getLogger().log("Unable to update city: " + id);
					context.getLogger().log(e.getMessage());
				}
				return new HttpResponse();
			} else {
				// insert new record
				context.getLogger()
						.log("Inserting into the table with id: " + id + " and temperature: " + temperature.getTemp());
				try {
					final Map<String, Object> infoMap = new HashMap<String, Object>();
					infoMap.put("t", temperature.getTemp());
					PutItemOutcome outcome = table
							.putItem(new Item().withPrimaryKey("sc", id).with("t", temperature.getTemp()));
					context.getLogger().log("PutItem succeeded:\n" + outcome.getPutItemResult());
				} catch (Exception e) {
					context.getLogger().log("Unable to add item: " + id + " " + temperature.getTemp());
					context.getLogger().log(e.getMessage());
				}

			}
		} else {
			context.getLogger().log("Could not find the data.");
		}

		return response;
	}
}
