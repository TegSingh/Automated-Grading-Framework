package Client;

import java.io.*;
import java.util.*;
import java.net.*;

public class Client {

    public Client() {
    }

    public static void main(String[] args) {

        boolean student_flag;
        int client_id = 0;
        // Socket active on port 3000
        try (Socket socket = new Socket("localhost", 3000)) {

            // Writer to send information to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Object to read from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Flag indicating if a client should proceed or not
            boolean login_flag = false;
            int credential_choice = 0;
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

                credential_choice = Integer.parseInt(credential_choice_input);
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
                    client_id = Integer.parseInt(id);
                } else if (server_response_login.equals("ID not found")) {
                    System.out.println("Entered ID not found");
                } else if (server_response_login.equals("Already logged in")) {
                    System.out.println("Student already in");
                } else {
                    System.out.println("Entered password not correct");
                }
            }

            // Set up the student flag value to indicate the type of client
            student_flag = credential_choice == 1 ? true : false;
            String print_data = student_flag ? " Student ID: " : " Instructor ID: ";
            System.out.println("Welcome" + print_data + client_id);

            // Login activity completed for client
            // ____________________________________________________________________________________________________________

            String line = "";
            boolean first_client_menu_flag = false;
            String input = "";
            // Begin the main loop following login
            if (student_flag) {
                while (!first_client_menu_flag && !line.equalsIgnoreCase("exit")) {

                    // Posting client specific menu
                    display_student_menu();
                    line = scanner.nextLine();

                    switch (line) {
                    case "1":
                        System.out.println("Student chose to check the list of pending assignments");
                        out.println("Check pending assignments");
                        break;
                    case "2":
                        System.out.println("Student chose to make a submission for an assignment");
                        out.println("Make submissions");
                        break;
                    case "3":
                        System.out.println("Student chose to log out and exit");
                        out.println("Log out");
                        return;
                    default:
                        System.out.println("Invalid input please try again");
                        continue;
                    }
                }
            } else {

                // Main loop for instructor
                while (!first_client_menu_flag && !line.equalsIgnoreCase("exit")) {

                    // Posting client specific menu
                    display_instructor_menu();
                    line = scanner.nextLine();

                    switch (line) {
                    case "1":
                        System.out.println("Instructor chose to post an assignment");
                        out.println("Post assignment");
                        break;
                    case "2":
                        System.out.println("Instructor chose to review submissions for the assignment");
                        out.println("Review submissions");
                        break;
                    case "3":
                        System.out.println("Instructor chose to log out and exit");
                        out.println("Log out");
                        return;
                    default:
                        System.out.println("Invalid input please try again");
                        continue;
                    }
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

    // Method to display the student menu
    public static void display_student_menu() {
        System.out.println("----------------------------------------");
        System.out.println("Make a selection");
        System.out.println("1. Check pending assignments");
        System.out.println("2. Submit an assignment");
        System.out.println("3. Log out");
        System.out.println("Enter exit to terminate process");
        System.out.println("----------------------------------------");
        System.out.println("Enter choice: ");

    }

    // Method to display the instructor menu
    public static void display_instructor_menu() {
        System.out.println("----------------------------------------");
        System.out.println("Make a selection");
        System.out.println("1. Post an assignment");
        System.out.println("2. Review submissions");
        System.out.println("3. Log out");
        System.out.println("Enter exit to terminate process");
        System.out.println("----------------------------------------");
        System.out.println("Enter Choice: ");
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