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

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleApiCall_WriteToFile_InputStream {

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
			
	        // Ensure the directory exists before writing to the file
	        Path tempFile = Path.of("resources/byteArray_output.dat");
	        Files.createDirectories(tempFile.getParent());
	        
	        
	       //Get the input stream from the response and write the input stream to a file incrementally (to avoid OutOfMemory)
	       try(InputStream inputStream= httpResponse.body(); 
	    	FileOutputStream fileOutputStream = new FileOutputStream(tempFile.toFile())){
	    	   byte[] buffer = new byte[8192];  // 8 KB buffer
	            int bytesRead;
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                fileOutputStream.write(buffer, 0, bytesRead);
	            }
	            System.out.println("File saved to: "+tempFile.toAbsolutePath());  
	    	      
	       }catch(IOException e) {
	    	   e.printStackTrace();
	       }
	        		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
