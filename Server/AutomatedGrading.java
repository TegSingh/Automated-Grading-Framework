package Server;

import Assignment.Assignment;

public class AutomatedGrading {

    public AutomatedGrading() {

    }

    // Method to write assignment to a file
    public boolean write_assignment_to_file(Assignment assignment, int student_id) {
        System.out.println("Write Assignment to file method called from Automated Grading");
        return false;
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
