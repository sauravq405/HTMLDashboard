package com.vt.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleApiCall {

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
			
			// Send the request and get the response
			HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());			
			
			// Print the response
	        System.out.println("Response Code: " + httpResponse.statusCode());
	        System.out.println("");
	        System.out.println("Response Body: " + httpResponse.body());
	        		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
