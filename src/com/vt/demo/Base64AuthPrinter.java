package com.vt.demo;

import java.util.Base64;

public class Base64AuthPrinter {
	
    public static void main(String[] args) {
        // Your username and password
        String username = "uname";
        String password = "pwd";
        
        // Concatenate username and password with a colon
        String authString = username + ":" + password;
        
        // Encode the string to Base64
        String base64Encoded = Base64.getEncoder().encodeToString(authString.getBytes());
        
        // Print the Base64 encoded token
        System.out.println("Auth-token: "+base64Encoded);
    }
}
