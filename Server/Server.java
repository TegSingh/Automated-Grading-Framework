package Server;

import java.io.*;
import java.net.*;
import java.util.*;

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
                    System.out.println(student.toString());

                }

                // Close the scanner object
                scanner.close();

            } catch (FileNotFoundException e) {
                System.out.println("Server: Could not find Student data file");
                e.printStackTrace();
            }

            return return_students;

        }

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
                    System.out.println(instructor.toString());
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

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(
                            "Client " + clientSocket.getInetAddress().getHostAddress().toString() + ": " + line);
                    out.println(line);
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
