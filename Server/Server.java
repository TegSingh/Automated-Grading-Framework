package Server;

import java.io.*;
import java.net.*;
import java.util.*;

import Assignment.Assignment;
import Instructor.Instructor;
import Student.Student;

public class Server {

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(3000);
            server.setReuseAddress(true);

            while (true) {
                // Socket object to connect incoming clients
                Socket client = server.accept();
                // Print the information about the new client
                System.out.println("New client connected: " + client.getInetAddress().getHostAddress().toString());

                // Create a client handler object
                ClientHandler clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        // List to store all Client(Student and Instructor) and assignment information
        private static ArrayList<Student> students = new ArrayList<>();
        private static ArrayList<Instructor> instructors = new ArrayList<>();
        private static ArrayList<Assignment> assignments = new ArrayList<>();
        Student logged_in_student = null;
        Instructor logged_in_instructor = null;
        AutomatedGrading auto_grader = new AutomatedGrading();

        // Fetch data from student file and store into arraylist
        public ArrayList<Student> read_student_list_file() {
            System.out.println("Server: Fetching Student data");

            // Create a return array list and a file object to read the file
            ArrayList<Student> return_students = new ArrayList<>();
            File student_list_file = new File("Server/student_list.txt");
            try {
                Scanner scanner = new Scanner(student_list_file);

                // Read data file line by line
                while (scanner.hasNextLine()) {

                    // Get the data and split to get the relevant substrings
                    String data = scanner.nextLine();
                    String data_substrings[] = data.split(", ");

                    // Get the parameters for the Student model constructor
                    String id_string = data_substrings[0];
                    int id = Integer.parseInt(id_string);
                    String password = data_substrings[1];
                    String name = data_substrings[2];
                    double grade = Double.parseDouble(data_substrings[3]);
                    int instructor_id = Integer.parseInt(data_substrings[4]);
                    boolean logged_in = data_substrings[5].equals("true") ? true : false;

                    // Create a student object and add it to the arraylist
                    Student student = new Student(id, password, name, grade, instructor_id, logged_in);
                    return_students.add(student);
                }

                // Close the scanner object
                scanner.close();

            } catch (FileNotFoundException e) {
                System.out.println("Server: Could not find Student data file");
                e.printStackTrace();
            }

            return return_students;

        }

        // Method to read instructor file
        public ArrayList<Instructor> read_instructor_list_file() {
            System.out.println("Server: Fetching Instructor data");

            // Create a return array list and a file object to read the file
            ArrayList<Instructor> return_instructors = new ArrayList<>();
            File instructor_list_file = new File("Server/instructor_list.txt");

            try {
                Scanner scanner = new Scanner(instructor_list_file);

                // Read data file line by line
                while (scanner.hasNextLine()) {

                    // Get the data and split to get the relevant substrings
                    String data = scanner.nextLine();
                    String data_substrings[] = data.split(", ");

                    // Get the parameters for the Student model constructor
                    String id_string = data_substrings[0];
                    int id = Integer.parseInt(id_string);
                    String password = data_substrings[1];
                    String name = data_substrings[2];
                    String course_code = data_substrings[3];
                    boolean logged_in = data_substrings[4].equals("true") ? true : false;

                    // Create a student object and add it to the arraylist
                    Instructor instructor = new Instructor(id, password, name, course_code, logged_in);
                    return_instructors.add(instructor);
                }

                // Close the scanner object
                scanner.close();

            } catch (FileNotFoundException e) {
                System.out.println("Server: Could not find Instructor data file");
                e.printStackTrace();
            }

            return return_instructors;
        }

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            students = read_student_list_file();
            instructors = read_instructor_list_file();
        }

        // Method to find a student with a certain ID in the database
        public Student find_student_in_string(int id) {

            Student student_found = null;

            // Loop throught the entire list to find the student
            // System.out.println("Server: Searching element ID in Student list");
            for (Student student : students) {
                if (student.get_id() == id) {
                    student_found = student;
                }
            }

            // If requested student not found
            if (student_found == null) {
                System.out.println("Student not found");
                return null;
            } else {
                // System.out.println("Server: " + student_found.toString());
            }

            return student_found;
        }

        // Method to find an element with a certain ID in the database
        public Instructor find_instructor_in_string(int id) {

            Instructor instructor_found = null;

            // Loop through the entire list to find the instructor
            for (Instructor instructor : instructors) {
                if (instructor.get_id() == id) {
                    instructor_found = instructor;
                }
            }

            // If requested instructor not found
            if (instructor_found == null) {
                System.out.println("Student not found");
                return null;
            } else {
            }

            return instructor_found;

        }

        // Method to validate login information for the Student
        public int validate_student(int id, String password) {
            // System.out.println("Server: Validate client method called");
            Student student = find_student_in_string(id);

            if (student != null) {

                System.out.println("Server: Found student, checking password");
                // Check whether the password in correct
                if (student.get_password().equals(password)) {
                    System.out.println("Server: Student validated, returning object");
                    return student.get_id();
                } else {
                    System.out.println("Server: Wrong password for instructor");
                    return 1;
                }

            } else {
                System.out.println("Server: Student not found");
                return 0;
            }
        }

        // Method to validate login information for the Instructor
        public int validate_instructor(int id, String password) {
            // System.out.println("Server: Validate instructor method called");
            Instructor instructor = find_instructor_in_string(id);

            if (instructor != null) {

                System.out.println("Server: Found instructor, checking password");
                // Check whether the password is correct
                if (instructor.get_password().equals(password)) {
                    System.out.println("Server: Instructor validated, returning object");
                    return instructor.get_id();
                } else {
                    System.out.println("Server: Wrong password for instructor");
                    return 1;
                }

            } else {
                System.out.println("Server: Instructor not found");
                return 0;
            }
        }

        // Method to print the list of students
        public void display_student_list() {
            for (Student student : students) {
                System.out.println(student.toString());
            }
        }

        // Method to print the list of instructors
        public void display_instructor_list() {
            for (Instructor instructor : instructors) {
                System.out.println(instructor.toString());
            }
        }

        // Method to display the IDs of the logged in clients
        public void display_logged_in_clients() {
            System.out.print("Student IDs: ");
            for (Student student : students) {
                if (student.is_logged_in()) {
                    System.out.print(student.get_id() + ", ");
                }
            }
            System.out.println();
            System.out.print("Instructor IDs: ");
            for (Instructor instructor : instructors) {
                if (instructor.is_logged_in()) {
                    System.out.print(instructor.get_id() + ", ");
                }
            }
            System.out.println();

        }

        // Method to get list of logged in students
        public ArrayList<Integer> get_logged_in_students() {
            ArrayList<Integer> return_students = new ArrayList<>();
            for (Student student : students) {
                if (student.is_logged_in()) {
                    return_students.add(student.get_id());
                }
            }
            return return_students;
        }

        // Method to get the list of logged in instructors
        public ArrayList<Integer> get_logged_in_instructors() {
            ArrayList<Integer> return_instructors = new ArrayList<>();
            for (Instructor instructor : instructors) {
                if (instructor.is_logged_in()) {
                    return_instructors.add(instructor.get_id());
                }
            }
            return return_instructors;
        }

        // Method to write change to database file
        public void write_change_to_database_file(int type) {
            if (type == 1) {
                try {
                    FileWriter database_file = new FileWriter("Server/student_list.txt", false);
                    for (Student student : students) {
                        database_file.write(student.toStringFile() + "\n");
                    }
                    database_file.close();
                } catch (Exception e) {
                    System.out.println("Error opening file");
                }
            } else if (type == 2) {
                try {
                    FileWriter database_file = new FileWriter("Server/instructor_list.txt", false);
                    for (Instructor instructor : instructors) {
                        database_file.write(instructor.toStringFile() + "\n");
                    }
                    database_file.close();
                } catch (Exception e) {
                    System.out.println("Error opening file");
                }
            }

        }

        // Override the runnable run() method
        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // Initialize the object
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                // Get the input stream
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Create a flag to check whether user login is done
                boolean login_flag = false;

                // Loop till the login activity is done while the client is still connected
                while (!login_flag && this.clientSocket.isConnected()) {

                    // This input will have users login credentials
                    System.out.println("New login while loop iteration");
                    String login_input = null;
                    try {
                        login_input = in.readLine();
                        String[] credentials = login_input.split(", ");
                        int type = Integer.parseInt(credentials[0]);
                        int id = Integer.parseInt(credentials[1]);
                        String password = credentials[2];

                        if (type == 1) {

                            // This is a student
                            int validated_student_id = validate_student(id, password);

                            // Get the list of currently logged in students
                            ArrayList<Integer> logged_in_students = get_logged_in_students();
                            boolean student_already_logged_in = logged_in_students.contains(validated_student_id);

                            if (validated_student_id == 0) {
                                System.out.println("Server: Student ID not found");
                                out.println("ID not found");
                            } else if (validated_student_id == 1) {
                                System.out.println("Server: Password not correct for student");
                                out.println("Password not correct");
                            } else if (student_already_logged_in) {
                                System.out.println("Server: Student already logged in");
                                out.println("Already logged in");
                            } else {
                                System.out.println("Server: Student found and validated");

                                // Set the student in arraylist as logged in and set the client handler ID
                                find_student_in_string(validated_student_id).set_logged_in(true);
                                write_change_to_database_file(1);

                                logged_in_student = find_student_in_string(validated_student_id);
                                out.println("Login successful");

                                // Set the flag to end the login activity loop
                                login_flag = true;
                            }

                        } else if (type == 2) {

                            // This is an instructor
                            int validated_instructor_id = validate_instructor(id, password);

                            // Get the list of currently logged in instructors
                            ArrayList<Integer> logged_in_instructors = get_logged_in_instructors();
                            boolean instructor_already_logged_in = logged_in_instructors
                                    .contains(validated_instructor_id);

                            if (validated_instructor_id == 0) {
                                System.out.println("Server: Instructor ID not found");
                                out.println("ID not found");
                            } else if (validated_instructor_id == 1) {
                                System.out.println("Server: Password not correct for instructor");
                                out.println("Password not correct");
                            } else if (instructor_already_logged_in) {
                                System.out.println("Server: Instructor already logged in");
                                out.println("Already logged in");
                            } else {
                                System.out.println("Server: Instructor found and validated");

                                // Set the instructor arraylist value to logged in and set the Client Handler ID
                                find_instructor_in_string(validated_instructor_id).set_logged_in(true);
                                write_change_to_database_file(2);

                                logged_in_instructor = find_instructor_in_string(validated_instructor_id);
                                out.println("Login successful");

                                // Set the flag to end the login activity loop
                                login_flag = true;
                            }
                        }

                    } catch (Exception e) {
                        // This is to handle an unexpected potential ctrl C termination of the client
                        System.out.println("Server: Client unexpectedly terminated");
                        if (logged_in_student != null) {
                            find_student_in_string(logged_in_student.get_id()).set_logged_in(false);
                            write_change_to_database_file(1);
                        }
                        if (logged_in_instructor != null) {
                            find_instructor_in_string(logged_in_instructor.get_id()).set_logged_in(false);
                            write_change_to_database_file(2);
                        }
                        break;
                    }
                }

                if (login_flag) {
                    if (logged_in_student == null) {
                        System.out.println("Server: Client logged in: " + logged_in_instructor.toString());
                    } else {
                        System.out.println("Server: Client logged in: " + logged_in_student.toString());
                    }
                } else {
                    System.out.println("Server: Could not complete login");
                }

                // Create an instance of the assignment helper class
                AssignmentHelper assignmentHelper = new AssignmentHelper();

                // Read the input from the client
                String client_input = null;
                try {
                    while (!(client_input = in.readLine()).equals("Exit")) {
                        System.out.println(client_input);
                        switch (client_input) {

                            // Student requested to make submissions
                            case "Make submissions":

                                // Get the list of assignments pending for a course
                                ArrayList<Integer> pending_assignments_to_submit = assignmentHelper
                                        .get_course_assignments(
                                                assignments,
                                                find_instructor_in_string(logged_in_student.get_instructor_id())
                                                        .get_course_code(),
                                                logged_in_student.get_id());

                                if (pending_assignments_to_submit.size() == 0) {
                                    System.out.println("No assignments to submit");
                                    out.println("No assignment found to submit");
                                    break;
                                } else {
                                    out.println(pending_assignments_to_submit.size());

                                }

                                for (Integer pending_assignment_id : pending_assignments_to_submit) {
                                    System.out.println(pending_assignment_id);
                                    out.println("Assignment ID:     " + pending_assignment_id + "    Course Code: "
                                            + find_instructor_in_string(logged_in_student.get_instructor_id())
                                                    .get_course_code());
                                }

                                // Get the ID the user wishes to submit
                                String submit_assignment_id_string = in.readLine();
                                System.out.println("Student " + logged_in_student.get_id()
                                        + ": Request to submit Assignment ID: " + submit_assignment_id_string);

                                // Submit the assignment based on ID entered by the user
                                if (submit_assignment_id_string.matches("[0-9]+")) {

                                    int submit_assignment_id = Integer.parseInt(submit_assignment_id_string);
                                    Assignment assignment_to_submit = assignmentHelper.get_assignment_by_id(assignments,
                                            submit_assignment_id);
                                    if (assignment_to_submit != null) {
                                        String[] submission_lines = assignment_to_submit.student_to_string()
                                                .split("\n");
                                        out.println(submission_lines.length);
                                        for (String submission_line : submission_lines) {
                                            out.println(submission_line);
                                        }
                                    } else {
                                        System.out.println("Server: Assignment not found");
                                        out.println("Assignment not found");
                                    }
                                } else {
                                    System.out.println("Server: Assignment ID not valid");
                                    break;
                                }

                                // Read the length of the students submission
                                int len = Integer.parseInt(in.readLine());
                                System.out.println("Length: " + len);
                                String[] complete_submission_lines = new String[len];
                                for (int i = 0; i < len; i++) {
                                    complete_submission_lines[i] = in.readLine();
                                    System.out.println(complete_submission_lines[i]);
                                }

                                Assignment complete_submission = new Assignment();
                                for (Assignment test_assignment : assignments) {
                                    // Get ID from the assignment lines
                                    for (String submission_line : complete_submission_lines) {
                                        if (submission_line.contains("Assignment ID")) {
                                            String[] submission_id = submission_line.split(": ");
                                            complete_submission.set_id(Integer.parseInt(submission_id[1]));
                                        }
                                    }
                                    if (test_assignment.get_id() == complete_submission.get_id()) {
                                        test_assignment.set_student_answers(assignmentHelper
                                                .decode_answers(complete_submission_lines));
                                        System.out.println(test_assignment.toString());
                                        complete_submission = test_assignment;
                                    }
                                }

                                if (complete_submission.get_id() != 0) {
                                    System.out.println(complete_submission.toString());
                                    out.println("Submitted successfully");

                                    // Write submission to a file
                                    auto_grader.write_assignment_to_file(complete_submission,
                                            logged_in_student.get_id());
                                    ArrayList<Integer> student_submissions = new ArrayList<>();
                                    for (Assignment test_assignment : assignments) {
                                        if (test_assignment.get_id() == complete_submission.get_id()) {
                                            student_submissions = test_assignment.get_student_submissions();
                                            student_submissions.add(logged_in_student.get_id());
                                            test_assignment.set_student_submissions(student_submissions);
                                            complete_submission.set_student_submissions(student_submissions);
                                        }
                                    }

                                    // Add the new assignment to the list of the submitteed assignmnets by student
                                    for (Student student : students) {
                                        if (student.get_id() == logged_in_student.get_id()) {
                                            ArrayList<Integer> submitted_assignments = student
                                                    .get_submitted_assignments();
                                            submitted_assignments.add(complete_submission.get_id());
                                            student.set_submitted_assignmets(submitted_assignments);
                                            System.out.println(
                                                    "Assignments submitted by student " + logged_in_student.get_id()
                                                            + ": " + submitted_assignments.toString());
                                            break;
                                        }
                                    }

                                    System.out.println(complete_submission.get_student_submissions().toString());

                                } else {
                                    System.out.println(complete_submission.toString());
                                    out.println("Could not submit assignment");
                                }

                                // Calculate grades automatically and return to student
                                float assignment = auto_grader.return_grades(complete_submission);
                                out.println(Float.toString(assignment));
                                break;

                            // Student requested a list a pending assignment
                            case "Check pending assignments":
                                System.out.println("Student " + logged_in_student.get_id()
                                        + ": Requested to check pending assignments");

                                // Find the pending assignments a student enrolled in a particular course has
                                String course_code_pending = find_instructor_in_string(
                                        logged_in_student.get_instructor_id()).get_course_code();
                                System.out.println(course_code_pending);

                                ArrayList<Integer> pending_assignments = assignmentHelper
                                        .get_course_assignments(assignments, course_code_pending,
                                                logged_in_student.get_id());
                                out.println(pending_assignments.size());
                                for (Integer pending_assignment_id : pending_assignments) {
                                    System.out.println(pending_assignment_id);
                                    out.println("Assignment ID:     " + pending_assignment_id + "    Course Code: "
                                            + find_instructor_in_string(logged_in_student.get_instructor_id())
                                                    .get_course_code());
                                }
                                break;

                            // Instructor request posting assignment
                            case "Post assignment":
                                System.out.println("Instructor " + logged_in_instructor.get_id()
                                        + ": Requested to post an assignment for course: "
                                        + logged_in_instructor.get_course_code());
                                out.println(logged_in_instructor.get_course_code());

                                // Get the length of assignment string for instructor
                                String assignment_length_string = in.readLine();
                                int assignment_length = 0;
                                if (assignment_length_string.matches("[0-9]+")) {
                                    assignment_length = Integer.parseInt(assignment_length_string);
                                } else {
                                    System.out.println("Server: Number format error, Could not post assignment");
                                    break;
                                }
                                String[] assignment_lines = new String[assignment_length];

                                for (int i = 0; i < assignment_length; i++) {
                                    assignment_lines[i] = in.readLine();
                                }

                                // Decode the assignment and add it to list
                                Assignment posted_assignment = assignmentHelper.decode_assignment(assignment_lines);
                                assignments.add(posted_assignment);

                                // Add the assignment to the list of posted assignments by the instructor
                                for (Instructor instructor : instructors) {
                                    if (instructor.get_id() == logged_in_instructor.get_id()) {
                                        ArrayList<Integer> posted_assignments = instructor.get_posted_assignments();
                                        posted_assignments.add(posted_assignment.get_id());
                                        instructor.set_posted_assignments(posted_assignments);
                                        System.out.println("Posted Assignments by Instructor "
                                                + logged_in_instructor.get_id() + ": " + posted_assignments.toString());
                                        break;
                                    }
                                }

                                System.out.println("Total Assignments: " + assignments.size());
                                break;

                            // Instructor request reviewing submissions - Autograding, manual review file
                            case "Review submissions":
                                System.out.println("Instructor " + logged_in_instructor.get_id()
                                        + ": Requested to review student submissions");

                                break;

                            // Instructor requested to post grades for student
                            case "Post grades":
                                System.out.println(
                                        "Instructor " + logged_in_instructor.get_id() + ": Requested to post grades");
                                break;

                            // Instructor or Student request Log out
                            case "Log out":
                                if (logged_in_student != null) {
                                    System.out.println("Student " + logged_in_student.get_id() + ": Logging out");
                                    find_student_in_string(logged_in_student.get_id()).set_logged_in(false);
                                    write_change_to_database_file(1);
                                }
                                if (logged_in_instructor != null) {
                                    System.out.println("Instructor " + logged_in_instructor.get_id() + ": Logging out");
                                    find_instructor_in_string(logged_in_instructor.get_id()).set_logged_in(false);
                                    write_change_to_database_file(2);
                                }
                                break;

                            default:
                                break;
                        }
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    if (logged_in_student != null) {
                        System.out.println("Student " + logged_in_student.get_id() + ": Closed unexpectedly");
                        find_student_in_string(logged_in_student.get_id()).set_logged_in(false);
                        write_change_to_database_file(1);
                    }
                    if (logged_in_instructor != null) {
                        System.out.println("Instructor " + logged_in_instructor.get_id() + ": Closed unexpectedly");
                        find_instructor_in_string(logged_in_instructor.get_id()).set_logged_in(false);
                        write_change_to_database_file(2);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
