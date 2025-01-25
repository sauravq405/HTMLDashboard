package com.vt.demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleApiCall_PrintResponseFromInputStream {

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
	        
	        
	       //Get the input stream from the response and write the input stream to a file incrementally (to avoid OutOfMemory)
	       try(InputStream inputStream= httpResponse.body()){
	    	   byte[] buffer = new byte[8192];  // 8 KB buffer
	            int bytesRead;
	            
	           // Use a StringBuilder to collect the string incrementally
	            StringBuilder responseBuilder = new StringBuilder();
	            
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	            	// Convert bytes to a string and append to the builder
	                responseBuilder.append(new String(buffer, 0, bytesRead));
	            }
	            
	            // Print the final response string
	            System.out.println(responseBuilder.toString());
	       }catch(IOException e) {
	    	   e.printStackTrace();
	       }
	        		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
