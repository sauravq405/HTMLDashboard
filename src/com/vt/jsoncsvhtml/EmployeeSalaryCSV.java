package com.vt.jsoncsvhtml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class EmployeeSalaryCSV {

    public static void main(String[] args) {
        // Define file path
        String filePath = "resources/employee_salaries.csv";

        // Define some departments and roles for the dummy data
        String[] departments = {"HR", "Finance", "Engineering", "Marketing", "Sales"};
        String[] roles = {"Manager", "Developer", "Analyst", "Executive", "Consultant"};
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
        String[] education = {"Bachelors", "Masters", "PhD"};

        // Create a random number generator
        Random rand = new Random();

        // Try to create the CSV file
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write the header row with 8 columns
            writer.append("Employee ID,Name,Department,Role,Salary,City,Education,Joining Date\n");

            // Generate 100 records
            for (int i = 1; i <= 100; i++) {
                // Generate random employee ID (6-digit number)
                String employeeId = String.format("%06d", rand.nextInt(1000000));
                
                // Generate a relevant employee name
                String name = generateEmployeeName(i);

                // Randomly select a department and role
                String department = departments[rand.nextInt(departments.length)];
                String role = roles[rand.nextInt(roles.length)];
                
                // Generate a random salary between 30,000 and 100,000
                int salary = 30000 + rand.nextInt(70000);
                
                // Randomly select a city
                String city = cities[rand.nextInt(cities.length)];
                
                // Randomly select an education level
                String educationLevel = education[rand.nextInt(education.length)];
                
                // Generate a random joining date (simulated)
                String joiningDate = (rand.nextInt(10) + 2010) + "-" + (rand.nextInt(12) + 1) + "-" + (rand.nextInt(28) + 1);

                // Write the record to the file
                writer.append(employeeId)
                      .append(",")
                      .append(name)
                      .append(",")
                      .append(department)
                      .append(",")
                      .append(role)
                      .append(",")
                      .append(String.valueOf(salary))
                      .append(",")
                      .append(city)
                      .append(",")
                      .append(educationLevel)
                      .append(",")
                      .append(joiningDate)
                      .append("\n");
            }

            System.out.println("CSV file created successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to generate a relevant employee name based on the employee number
    private static String generateEmployeeName(int i) {
        String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Emily", "James", "Olivia", "William", "Sophia"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin"};
        
        String firstName = firstNames[i % firstNames.length];
        String lastName = lastNames[i % lastNames.length];
        
        return firstName + " " + lastName;
    }
}
