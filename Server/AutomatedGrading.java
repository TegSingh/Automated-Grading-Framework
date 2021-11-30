package Server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import Assignment.Assignment;

public class AutomatedGrading {

    public AutomatedGrading() {

    }

    // Method to write assignment to a file
    public boolean write_assignment_to_file(Assignment assignment, int student_id) {
        System.out.println("Write Assignment to file method called from Automated Grading");

        String filename = assignment.get_course_code() + "-" + assignment.get_id() + "_" + Integer.toString(student_id)
                + ".txt";
        String localDir = System.getProperty("user.dir");
        String complete_path = localDir + "\\Server\\Submissions\\" + filename;

        // Create the file
        try {
            FileOutputStream file = new FileOutputStream(complete_path);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create file");
        }

        // Write to file
        try {
            FileWriter fileWriter = new FileWriter(complete_path);
            String[] assignment_lines = assignment.toString().split("\n");
            for (String assignment_line : assignment_lines) {
                fileWriter.write(assignment_line);
                fileWriter.write("\n");
            }
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not write to file");
        }

        return false;
    }

    // Method to return string based by reading the file
    public String file_to_string(File file) {
        String file_data = "";
        try {
            file_data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            System.out.println("Cannot read file");
            e.printStackTrace();
        }
        return file_data;
    }

    // Method to get students final grade
    public float return_grades(Assignment assignment) {
        System.out.println("Method to calculate grades called from the Automated grading");
        float final_grade = 0;
        int i = 0;
        for (String student_answer : assignment.get_student_answers()) {
            if (student_answer.equals(assignment.get_instructor_answers().get(i))) {
                final_grade += 1.0;
            }
            i++;
        }

        final_grade /= (assignment.get_instructor_answers().size());
        final_grade *= 100.0;

        System.out.println("Calculated grade: " + final_grade);
        return final_grade;
    }

    // Main method to test the above defined function
    public static void main(String[] args) {

    }
}
