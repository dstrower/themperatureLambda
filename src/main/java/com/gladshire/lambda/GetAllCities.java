package com.gladshire.lambda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.gladshire.model.HttpCityResponse;
import com.gladshire.model.HttpQuerystringRequest;

public class GetAllCities implements RequestHandler<HttpQuerystringRequest, HttpCityResponse> {

	@Override
	public HttpCityResponse handleRequest(HttpQuerystringRequest request, Context context) {
		
		context.getLogger().log("Getting all cities.");
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDB dynamoDB = new DynamoDB(client);
        TreeSet<String> citySet = new TreeSet<>();
		// Get a reference to the Widget table
		Table table = dynamoDB.getTable("weather");
		try {
			ItemCollection<ScanOutcome> items = table.scan();
			Iterator<Item> iter = items.iterator();
            while (iter.hasNext()) {
                Item item = iter.next();
                String city = (String) item.get("sc");
                context.getLogger().log(city);
                citySet.add(city);
            }
		} catch (Exception e) {
			context.getLogger().log("Unable to scan the table:");
			context.getLogger().log(e.getMessage());
		}
		List<String> cityList = new ArrayList<>(citySet);
		return new HttpCityResponse(cityList);
	}
}
