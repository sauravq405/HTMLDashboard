package com.vt.demo;

import java.io.InputStream;

public class ResourceLoader {
    public static void main(String[] args) {
        //String resourceName = "config.properties"; // Replace with your file name
        String resourceName = "ABC.txt"; // Replace with your file name
        InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(resourceName);

        if (inputStream != null) {
            System.out.println("Resource found: " + resourceName);
            // Process the resource as needed
        } else {
            System.out.println("Resource not found: " + resourceName);
        }
    }
}

