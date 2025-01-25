package com.vt.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleApiCall_WriteToFile_ByteArray {

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
			HttpResponse<byte[]> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
			System.out.println("HTTP Response code: "+httpResponse.statusCode());
			byte[] body = httpResponse.body();

			// Ensure the directory exists before writing to the file
			Path tempFile = Path.of("resources/byteArray_output.dat");
			Files.createDirectories(tempFile.getParent());
			
			// Process or save the response body incrementally to avoid memory overflow
			Files.write(tempFile, body, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			
			System.out.println("File saved to: " + tempFile.toAbsolutePath());
	        		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
