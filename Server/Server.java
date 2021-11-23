package Server;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.text.html.HTMLDocument.RunElement;

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
        // List to store all Client(Student and Instructor) information
        private static ArrayList<Student> students = new ArrayList<>();
        private static ArrayList<Instructor> instructors = new ArrayList<>();

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

                    // Create a student object and add it to the arraylist
                    Student student = new Student(id, password, name, grade, instructor_id);
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

                    // Create a student object and add it to the arraylist
                    Instructor instructor = new Instructor(id, password, name, course_code);
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

        // Override the runnable run() method
        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // Initialize the object
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                // Get the input stream
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Manage the user login
                String input = "";

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

                            if (validated_student_id == 0) {
                                System.out.println("Server: Student ID not found");
                                out.println("ID not found");
                            } else if (validated_student_id == 1) {
                                System.out.println("Server: Password not correct for student");
                                out.println("Password not correct");
                            } else {
                                System.out.println("Server: Student found and validated");
                                out.println("Login successful");
                                // Set the flag to end the login activity loop
                                login_flag = true;
                            }

                        } else if (type == 2) {

                            // This is an instructor
                            int validated_instructor_id = validate_instructor(id, password);

                            if (validated_instructor_id == 0) {
                                System.out.println("Server: Instructor ID not found");
                                out.println("ID not found");
                            } else if (validated_instructor_id == 1) {
                                System.out.println("Server: Password not correct for instructor");
                                out.println("Password not correct");
                            } else {
                                System.out.println("Server: Instructor found and validated");
                                out.println("Login successful");
                                // Set the flag to end the login activity loop
                                login_flag = true;
                            }
                        }

                    } catch (NullPointerException e) {
                        // This is to handle an unexpected potential ctrl C termination of the client
                        System.out.println("Client unexpectedly terminated");
                        break;
                    }
                }

                if (login_flag) {
                    System.out.println("Server: Login completed for client");
                } else {
                    System.out.println("Server: Could not complete login");
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

        // Method to find a student with a certain ID in the database
        public Student find_student_in_string(int id) {

            Student student_found = null;

            // Loop throught the entire list to find the student
            System.out.println("Server: Searching element ID in Student list");
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
                System.out.println(student_found.toString());
            }

            return student_found;
        }

        // Method to find an element with a certain ID in the database
        public Instructor find_instructor_in_string(int id) {

            Instructor instructor_found = null;

            // Loop through the entire list to find the instructor
            System.out.println("Server: Search element ID in Instructor list");
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
                System.out.println(instructor_found.toString());
            }

            return instructor_found;

        }

        // Method to validate login information for the Student
        public int validate_student(int id, String password) {
            System.out.println("Server: Validate client method called");
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
            System.out.println("Server: Validate instructor method called");
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

    }
}
