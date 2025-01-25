package com.vt.demo;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class SimpleApiCall_WithoutMemoryLoading {
	public static void main(String[] args) {
		try {
			// Create an HTTP client
			HttpClient httpClient = HttpClient.newHttpClient();

			// Define the API URL
			String apiUrl = "https://jsonplaceholder.typicode.com/posts";

			// Create an HTTP GET request
			HttpRequest httpRequest = HttpRequest.newBuilder()
					.uri(URI.create(apiUrl))
					.header("Authorization", "Bearer your_token")
					.header("Content-Type", "application/json") //this header is for POST requests, doesn't harm even if we use it with GET 
					.GET().build();
			
			// Send the request and get the response as an InputStream
	        HttpResponse<InputStream> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
	        System.out.println("Response Code: " + httpResponse.statusCode());
	        
	        //Create a Jackson JsonFactory to be used later
	        JsonFactory factory = new JsonFactory();
	       
	        //Get the input stream from the response and write the input stream to a file incrementally (to avoid OutOfMemory)
	        try (InputStream inputStream = httpResponse.body();
	        	    JsonParser parser = factory.createParser(inputStream)) {
	        	    int indentLevel = 0;
	        	    boolean needsComma = false; // Tracks whether to add a comma
	        	    while (!parser.isClosed()) {
	        	        JsonToken token = parser.nextToken();

	        	        if (token == null) {
	        	            break;
	        	        }
	        	        switch (token) {
	        	            case START_OBJECT, START_ARRAY -> {
	        	                if (needsComma) {
	        	                    System.out.println(","); // Add a comma before the next object/array
	        	                }
	        	                printIndented(token == JsonToken.START_OBJECT ? "{" : "[", indentLevel);
	        	                indentLevel++;
	        	                needsComma = false; // Reset for nested structures
	        	            }
	        	            case END_OBJECT, END_ARRAY -> {
	        	                indentLevel--;
	        	                System.out.println(); // Print a new line before closing
	        	                printIndented(token == JsonToken.END_OBJECT ? "}" : "]", indentLevel);
	        	                needsComma = true; // Mark for next sibling to have a comma
	        	            }
	        	            case FIELD_NAME -> {
	        	                if (needsComma) {
	        	                    System.out.print(","); // Add a comma before field name
	        	                }
	        	                System.out.print(" ".repeat(indentLevel * 2) + "\"" + parser.getCurrentName() + "\": ");
	        	                needsComma = true; // After a field name, expect a comma before next item
	        	            }
	        	            case VALUE_STRING -> {
	        	                System.out.println("\"" + parser.getValueAsString() + "\"");
	        	                needsComma = true; // Mark that the next value or field needs a comma
	        	            }
	        	            case VALUE_NUMBER_INT, VALUE_NUMBER_FLOAT -> {
	        	                System.out.println(parser.getNumberValue());
	        	                needsComma = true; // Mark for next value or field
	        	            }
	        	            case VALUE_TRUE, VALUE_FALSE -> {
	        	                System.out.println(parser.getBooleanValue());
	        	                needsComma = true; // Mark for next value or field
	        	            }
	        	            case VALUE_NULL -> {
	        	                System.out.println("null");
	        	                needsComma = true; // Mark for next value or field
	        	            }
	        	            default -> {
	        	                // Do nothing for other tokens
	        	            }
	        	        }
	        	    }
	        	} 		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Helper method to add indentation for formatting
	private static void printIndented(String text, int indentLevel) {
        System.out.print(" ".repeat(indentLevel * 2));
        System.out.println(text);
    }

}
