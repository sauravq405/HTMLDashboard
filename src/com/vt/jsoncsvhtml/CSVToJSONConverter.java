package com.vt.jsoncsvhtml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVToJSONConverter {

    public static void main(String[] args) {
        String csvFile = "resources/employee_salaries.csv";  // CSV file path
        String jsonFile = "resources/employee_salaries.json";  // Output JSON file path

        // Initialize a list to store employee records as maps
        List<Map<String, String>> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            // Read the header line (column names)
            String[] headers = reader.readLine().split(",");

            // Read each subsequent line (employee data)
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                
                // Create a map to store the employee data
                Map<String, String> employee = new HashMap<>();
                
                // Map CSV columns to employee data
                for (int i = 0; i < headers.length; i++) {
                    employee.put(headers[i], values[i]);
                }

                // Add the employee map to the list of employees
                employees.add(employee);
            }

            // Write the list of employees to the output JSON file
            try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                fileWriter.write("[\n"); // Start the JSON array
                for (int i = 0; i < employees.size(); i++) {
                    Map<String, String> employee = employees.get(i);
                    fileWriter.write("  {\n");
                    int j = 0;
                    // Write each key-value pair in the employee map as a JSON field
                    for (Map.Entry<String, String> entry : employee.entrySet()) {
                        fileWriter.write("    \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");
                        if (j < employee.size() - 1) {
                            fileWriter.write(",\n");
                        } else {
                            fileWriter.write("\n");
                        }
                        j++;
                    }
                    if (i < employees.size() - 1) {
                        fileWriter.write("  },\n");
                    } else {
                        fileWriter.write("  }\n");
                    }
                }
                fileWriter.write("]\n"); // End the JSON array
            }

            System.out.println("CSV file successfully converted to JSON and saved to: " + jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
