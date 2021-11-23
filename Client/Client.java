package Client;

import java.io.*;
import java.util.*;

import Student.Student;

import java.net.*;

public class Client {

    public static void main(String[] args) {

        // Socket active on port 3000
        try (Socket socket = new Socket("localhost", 3000)) {

            // Writer to send information to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Object to read from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Flag indicating if a client should proceed or not
            boolean login_flag = false;

            Scanner scanner = new Scanner(System.in);

            // Loop until the login activity is done
            while (!login_flag) {

                // Create an arraylist containing all login information to be sent to server
                ArrayList<String> credentials = new ArrayList<>();
                String credential_choice_input;

                // Display the login menu and get user choice
                System.out.print("Are you a...\n1) Student\n2) Instructor\n");
                System.out.println("Enter choice: ");
                credential_choice_input = scanner.nextLine();
                credentials.add(credential_choice_input);

                int credential_choice = Integer.parseInt(credential_choice_input);
                if (credential_choice != 1 && credential_choice != 2) {
                    System.out.println("Invalid input, Please try again");
                    continue;
                }

                // Create a variable to hold Login information
                System.out.print("Enter ID: ");
                boolean id_valid = false;
                String id = null;
                while (!id_valid) {
                    id = scanner.nextLine();
                    // Check if ID has valid values
                    if (id.matches("[0-9]+")) {
                        id_valid = true;
                    } else {
                        System.out.print("ID can only contain numbers, Please enter valid ID: ");
                        continue;
                    }
                }

                credentials.add(id);

                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                credentials.add(password);

                // Send the login information to the server
                out.println(arraylist_to_string(credentials));

                String server_response_login = in.readLine();
                // Validation carried out on the server
                if (server_response_login.equals("Login successful")) {
                    System.out.println("Login successful");
                    login_flag = true;
                } else if (server_response_login.equals("ID not found")) {
                    System.out.println("Entered ID not found");
                } else {
                    System.out.println("Entered password not correct");
                }
            }

            if (login_flag) {
                System.out.println("Login activity done");
            } else {
                // End the function if the user input is exit
                return;
            }

            // Begin the main loop following login
            Scanner sc = new Scanner(System.in);
            String line = "";
            while (!(line = sc.nextLine()).equals("exit")) {
                System.out.println("Main client loop begins");
                while (sc.hasNextLine()) {
                    line = sc.next() + sc.nextLine();
                }
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to manage user login
    public static String[] handle_login() {
        String[] login_credentials = new String[2];
        Scanner sc = new Scanner(System.in);

        sc.close();
        return login_credentials;
    }

    // Method to convert Arraylist to string
    public static String arraylist_to_string(ArrayList<String> arraylist) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String credential : arraylist) {
            stringBuilder.append(credential).append(", ");
        }
        return stringBuilder.toString();
    }

}