package stms;

import java.io.*;
import java.util.*;

public class Delfin {
    // Method to input student records
    public static void input() {
        Scanner scanner = new Scanner(System.in);
        String csvFile = "src\\StudentInfo.csv"; 

        // Prompt user for input
        System.out.println("Enter the number of records you want to input:");
        int numberOfRecords = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        File file = new File(csvFile);
        boolean isNewFile = false;

        // Check if the file exists and is empty
        if (!file.exists()) {
            isNewFile = true; // File does not exist, so it will be created
        } else if (file.length() == 0) {
            isNewFile = true; // File exists but is empty
        }

        try (FileWriter writer = new FileWriter(csvFile, true)) { // Open in append mode
            // Write the header only if the file is new or empty
            if (isNewFile) {
                writer.append("Name,Age,Gender,Birthday,LRN,Guardian,Contact,Address\n");
            }

            // Collect user input and write to CSV
            for (int i = 0; i < numberOfRecords; i++) {
                System.out.println("Enter name:");
                String name = scanner.nextLine();

                System.out.println("Enter age:");
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                System.out.println("Enter gender:");
                String gender = scanner.nextLine();

                System.out.println("Enter birthday:");
                String bday = scanner.nextLine();

                String lrn;
                do {
                    System.out.println("Enter LRN (12 digits):");
                    lrn = scanner.nextLine();
                    if (lrn.length() != 12 || !lrn.matches("\\d{12}")) {
                        System.out.println("Invalid LRN. It must be exactly 12 digits.");
                    }
                } while (lrn.length() != 12 || !lrn.matches("\\d{12}"));

                System.out.println("Enter parent name:");
                String pn = scanner.nextLine();

                System.out.println("Enter parent contact number:");
                String pc = scanner.nextLine();

                System.out.println("Enter address:");
                String address = scanner.nextLine();

                // Write the data to the CSV file
                writer.append(name)
                      .append(",")
                      .append(String.valueOf(age))
                      .append(",")
                      .append(gender)
                      .append(",")
                      .append(bday)
                      .append(",")
                      .append(lrn)
                      .append(",")
                      .append(pn)
                      .append(",")
                      .append(pc)
                      .append(",")
                      .append(address)
                      .append("\n");
            }

            System.out.println("Data has been written to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to remove a student record by name (case-insensitive)
    public static void remove(String nameToRemove) {
        String csvFile = "src\\StudentInfo.csv";
        File inputFile = new File(csvFile);
        File tempFile = new File("temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean headerWritten = false;
            boolean recordFound = false;

            while ((currentLine = reader.readLine()) != null) {
                // Write the header to the temp file if it's the first line
                if (!headerWritten) {
                    writer.write(currentLine);
                    writer.newLine();
                    headerWritten = true;
                    continue;
                }

                // Split the line into fields
                String[] fields = currentLine.split(",");

                // Check if the name matches the one to remove (case-insensitive)
                if (fields[0].equalsIgnoreCase(nameToRemove)) {
                    recordFound = true; // Mark the record as found
                    continue; // Skip writing this line to the temp file
                }

                // Write the line to the temp file if it's not a match
                writer.write(currentLine);
                writer.newLine();
            }

            if (!inputFile.delete()) {
                System.out.println("Could not delete the original file");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename the temp file");
                return;
            }

            if (recordFound) {
                System.out.println("Record removed successfully.");
            } else {
                System.out.println("Record not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
