package Client;

import java.io.*;
import java.util.*;

import Assignment.Assignment;
import Client.AssignmentHelper;

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
                    System.out.println("Client already logged in");
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

            // Begin the main loop following login
            if (student_flag) {
                while (!first_client_menu_flag && !line.equalsIgnoreCase("exit")) {

                    // Posting client specific menu
                    display_student_menu();
                    line = scanner.nextLine();

                    switch (line) {

                        // Student chose to check the list of pending assignments for the course they
                        // are enrolled in
                        case "1":
                            System.out.println("Student chose to check the list of pending assignments");
                            out.println("Check pending assignments");
                            System.out.println("----------------------------------------");
                            System.out.println("Following are your pending assignments");
                            int num = Integer.parseInt(in.readLine());
                            for (int i = 0; i < num; i++) {
                                String pending_assignment = in.readLine();
                                System.out.println(pending_assignment);
                            }

                            // Empty the list of pending assignments
                            System.out.println("----------------------------------------");
                            break;

                        case "2":
                            out.println("Make submissions");
                            int available_assignments = 0;
                            String available_assignments_string = in.readLine();
                            if (available_assignments_string.equals("No assignment found to submit")) {
                                System.out.println("No assignments available to submit");
                                break;
                            } else {
                                available_assignments = Integer.parseInt(available_assignments_string);
                            }
                            for (int i = 0; i < available_assignments; i++) {
                                String assignments_to_submit = in.readLine();
                                System.out.println(assignments_to_submit);
                            }

                            System.out.println("Enter Assignment ID to complete: ");
                            String assignment_id = scanner.nextLine();
                            out.println(assignment_id);

                            String num_lines_string = in.readLine();
                            if (num_lines_string.equals("Assignment not found")) {
                                System.out.println("Assignment not found on Server");
                                break;
                            }

                            int num_lines = Integer.parseInt(num_lines_string);
                            String[] submission_lines = new String[num_lines];
                            for (int i = 0; i < num_lines; i++) {
                                submission_lines[i] = in.readLine();
                            }

                            AssignmentHelper assignmentHelper = new AssignmentHelper();
                            Assignment submission = assignmentHelper.decode_assignment(submission_lines);

                            // Get inputs from the student for answers to the assignment
                            int counter = 0;

                            ArrayList<String> student_answers = new ArrayList<>();
                            System.out.println("----------------------------------------\nAssignment ID: ");
                            submission.get_id();
                            System.out.println(submission.get_course_code());
                            System.out.println("----------------------------------------\n");

                            for (String question : submission.get_questions()) {
                                // Print the question
                                System.out.println(
                                        submission.question_to_string(question, submission.get_choices().get(counter)));
                                System.out.println("Enter your choice: ");
                                student_answers.add(scanner.nextLine());
                                counter++;
                            }

                            // Set the submission from the student as the input from the arraylist
                            submission.set_student_answers(student_answers);
                            String[] submission_lines_student = submission.submission_to_string().split("\n");
                            // Send the number of lines in the submission string to the server
                            out.println(submission_lines_student.length);
                            for (String submission_line_student : submission_lines_student) {
                                System.out.println(submission_line_student);
                                out.println(submission_line_student);
                            }

                            String submission_confirmation = in.readLine();
                            System.out.println(submission_confirmation);
                            // Check if the assignment was submitted successfully
                            if (submission_confirmation.equals("Submitted successfully")) {
                                System.out.println("Assignment submitted successfully");
                                System.out.println("Final Grade: " + in.readLine());
                                System.out.println(
                                        "NOTE: This is an automatically generated grade. The instructor will manually review your results if required");
                            } else {
                                System.out.println(submission_confirmation);
                                System.out.println("Could not submit assignment");
                            }

                            break;

                        case "3":
                            System.out.println("Student chose to Check grades");
                            out.println("Check grades");
                            // Read the size of the list of submitted assignments
                            int num_assignments = Integer.parseInt(in.readLine());
                            if (num_assignments == 0) {
                                System.out.println("No Grades posted yet");
                                break;
                            }
                            // Display the student grades
                            for (int i = 0; i < num_assignments; i++) {
                                System.out.println(in.readLine());
                            }

                            break;

                        case "4":
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

                        // Execute when instructor choses to post an assignment
                        case "1":

                            System.out.println("Instructor chose to post an assignment");
                            out.println("Post assignment");
                            Scanner sc = new Scanner(System.in);

                            // Make sure to enter the digits
                            System.out.println("Enter the number of questions");
                            String num_questions_string = sc.nextLine();
                            int num_questions = 0;
                            if (num_questions_string.matches("[0-9]+")) {
                                num_questions = Integer.parseInt(num_questions_string);
                            } else {
                                System.out.println("Enter valid number input");
                                continue;
                            }

                            // Create an assignment object
                            Assignment assignment = new Assignment();
                            String input_post_assignment = "";
                            ArrayList<String> questions = new ArrayList<>();
                            ArrayList<String[]> choices = new ArrayList<String[]>();
                            ArrayList<String> instructor_answers = new ArrayList<>();

                            // Generate a random number of ID
                            Random rand = new Random();
                            int assignment_id = rand.nextInt(8999) + 1000;
                            assignment.set_id(assignment_id);

                            // Get the value for Course code
                            String course_code = in.readLine();
                            System.out.println("Course code: " + course_code);
                            assignment.set_course_code(course_code);

                            for (int i = 1; i <= num_questions; i++) {
                                String[] mcq = new String[4];
                                System.out.println("Enter Question " + i + ":");
                                input_post_assignment = sc.nextLine();
                                questions.add(input_post_assignment);

                                // Get the choices for the questions
                                for (int j = 1; j <= 4; j++) {
                                    System.out.println("Enter choice " + j + ": ");
                                    mcq[j - 1] = sc.nextLine();
                                }
                                choices.add(mcq);

                                System.out.println("The question is: ");

                                // Get the input for instructors answer
                                System.out.print("Enter answer for the question: ");
                                input_post_assignment = sc.nextLine();
                                instructor_answers.add(input_post_assignment);
                            }

                            assignment.set_questions(questions);
                            assignment.set_choices(choices);
                            assignment.set_instructor_answers(instructor_answers);

                            // Display the assignment as a string
                            System.out.println(assignment.instructor_to_string());
                            System.out.println("Are you happy with this assignment?");
                            System.out.println(
                                    "NOTE: If you answer no, all you progress will be lost and you will be taken to the previous menu");
                            System.out.println("NOTE: Answering yes will post the assignment");
                            input_post_assignment = sc.nextLine();
                            if (input_post_assignment.equals("yes")) {
                                System.out.println("Generating Assignment....");
                                int i = 0;
                                while (i < 100) {
                                    i += 1;
                                }
                                System.out.println("Assignment created....");
                                i = 0;
                                while (i < 100) {
                                    i += 1;
                                }
                                // Send the assignment to the server
                                String[] instructor_string = assignment.instructor_to_string().split("\n");
                                // Get the length of the assignment string
                                out.println(instructor_string.length);
                                for (String assignment_line : instructor_string) {
                                    out.println(assignment_line);
                                }
                            } else {
                                System.out.println("You will be taken back to the previous menu");
                                continue;
                            }

                            break;

                        // Execute when the instructor chosen to review submissions
                        case "2":
                            System.out.println("Instructor chose to Manually review submissions for the assignment");
                            out.println("Review submissions");

                            String num_posted_string = in.readLine();
                            for (int i = 0; i < Integer.parseInt(num_posted_string); i++) {
                                System.out.println(in.readLine());
                            }
                            System.out.println("Enter the assignment you want to get submissions for: ");
                            String requested_assignment = scanner.nextLine();
                            out.println(requested_assignment);
                            System.out.println("Downloading submissions....");

                            int num_files = Integer.parseInt(in.readLine());
                            for (int i = 0; i < num_files; i++) {

                                String filepath = in.readLine();
                                String filename = filepath.substring(filepath.length() - 22, filepath.length());
                                System.out.println(filename);
                                String localDir = System.getProperty("user.dir");
                                localDir += "\\Client\\Submissions\\";
                                localDir += filename;
                                int file_line_num = Integer.parseInt(in.readLine());
                                FileOutputStream submission_file = new FileOutputStream(localDir);
                                submission_file.close();
                                FileWriter submission_file_writer = new FileWriter(localDir);

                                // Write to the new file line by line
                                for (int j = 0; j < file_line_num; j++) {
                                    String submission_file_data = in.readLine();
                                    System.out.println(submission_file_data);
                                    submission_file_writer.write(submission_file_data);
                                    submission_file_writer.write("\n");
                                }
                                submission_file_writer.close();
                            }
                            break;

                        // Instructor chose to log out and exit
                        case "3":
                            System.out.println("Instructor chose to Post Grades");
                            out.println("Post grades");

                            int num_assignments = Integer.parseInt(in.readLine());
                            for (int i = 0; i < num_assignments; i++) {
                                System.out.println(in.readLine());
                            }

                            // Get the assignment value to grade
                            System.out.println("Chose Assignment to grade: ");
                            String id_string = scanner.nextLine();
                            while (!id_string.matches("[0-9]+")) {
                                System.out.println("Invalid Assignment input try again");
                                System.out.println("Chose Assignment to grade: ");
                                id_string = scanner.nextLine();
                                if (id_string.equalsIgnoreCase("exit")) {
                                    break;
                                }
                            }

                            if (id_string.equalsIgnoreCase("exit")) {
                                break;
                            }

                            out.println(id_string);
                            String submission_available = in.readLine();
                            if (submission_available.equals("No submissions yet")) {
                                System.out.println("No submissions have been made for this assignment yet");
                                break;
                            } else {
                                System.out.println("Following is the students who submitted the assignment");
                                System.out.println(submission_available);
                            }

                            boolean chosen_student = false;
                            System.out.println("Enter ID of student to grade: ");
                            String chosen_student_id = scanner.nextLine();
                            while (!chosen_student) {
                                if (submission_available.contains(chosen_student_id)) {
                                    chosen_student = true;
                                } else {
                                    System.out.println("Chose a valid student from: " + submission_available);
                                    chosen_student_id = scanner.nextLine();
                                }
                            }

                            String grade_string = "";
                            Float grade = (float) 0;
                            boolean grade_entered = false;
                            if (chosen_student) {
                                System.out.println("Student chosen successfully. Enter Grade: ");
                                grade_string = scanner.nextLine();
                                while (!grade_entered) {
                                    try {
                                        grade = Float.parseFloat(grade_string);
                                        grade_entered = true;
                                    } catch (Exception e) {
                                        System.out.println("Wrong float format try again! Enter grade ");
                                        grade_string = scanner.nextLine();
                                    }
                                }
                                System.out.println("Sending grade.....");

                            } else {
                                System.out.println("No such student available");
                                break;
                            }

                            out.println(chosen_student_id + "," + grade);
                            System.out.println("Grade updated");
                            continue;
                        case "4":
                            System.out.println("Instructor chose to log out");
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

    // Method to display the student menu
    public static void display_student_menu() {
        System.out.println("----------------------------------------");
        System.out.println("Make a selection");
        System.out.println("1. Check pending assignments");
        System.out.println("2. Submit an assignment");
        System.out.println("3. Check Assignment grades");
        System.out.println("4. Log out");
        System.out.println("Enter exit to terminate process");
        System.out.println("----------------------------------------");
        System.out.println("Enter choice: ");

    }

    // Method to display the instructor menu
    public static void display_instructor_menu() {
        System.out.println("----------------------------------------");
        System.out.println("Make a selection");
        System.out.println("1. Post an assignment");
        System.out.println("2. Manually Review submissions");
        System.out.println("3. Post Grades");
        System.out.println("4. Log out");
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