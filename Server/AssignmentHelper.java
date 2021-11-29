package Server;

import java.util.ArrayList;

import Assignment.Assignment;

public class AssignmentHelper {

    public AssignmentHelper() {
        // Empty constructor since this is a utility class
    }

    public Assignment decode_assignment(String[] assignment_lines) {
        Assignment assignment = new Assignment();
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String[]> choices = new ArrayList<String[]>();
        ArrayList<String> instructor_answers = new ArrayList<>();
        ArrayList<String> assignment_choices = new ArrayList<>();
        ArrayList<String> student_answers = new ArrayList<>();

        // Initialize the assignment object by decoding the client messages
        for (String assignment_line : assignment_lines) {

            if (assignment_line.contains("Assignment ID:")) {
                String[] id_line = assignment_line.split(": ");
                assignment.set_id(Integer.parseInt(id_line[1]));
            }

            if (assignment_line.contains("SOFE")) {
                assignment.set_course_code(assignment_line);
            }

            if (assignment_line.equals("\n")) {
                continue;
            }

            if (assignment_line.contains("-----")) {
                continue;
            }

            if (assignment_line.contains("QUESTION")) {
                String[] question = assignment_line.split(": ");
                questions.add(question[1]);
            }

            if (assignment_line.contains("ANSWER: ")) {
                String[] answer = assignment_line.split(": ");
                student_answers.add(answer[1]);
            }

            if (assignment_line.contains("1. ")) {
                assignment_choices.add(assignment_line.substring(3));
            }

            if (assignment_line.contains("2. ")) {
                assignment_choices.add(assignment_line.substring(3));
            }

            if (assignment_line.contains("3. ")) {
                assignment_choices.add(assignment_line.substring(3));
            }

            if (assignment_line.contains("4. ")) {
                assignment_choices.add(assignment_line.substring(3));
            }

            if (assignment_choices.size() == 4) {
                String[] mcq = new String[4];
                for (int i = 0; i < assignment_choices.size(); i++) {
                    mcq[i] = assignment_choices.get(i);
                }
                // Empty the choices
                assignment_choices.clear();
                choices.add(mcq);
            }

            if (assignment_line.contains("The correct option is: ")) {
                String[] instructor_answer_line = assignment_line.split(": ");
                instructor_answers.add(instructor_answer_line[1]);
            }

        }

        assignment.set_choices(choices);
        assignment.set_instructor_answers(instructor_answers);
        assignment.set_questions(questions);
        System.out.println(assignment.instructor_to_string());
        return assignment;
    }

    // Method to get answers from the student
    public ArrayList<String> decode_answers(String[] assignment_lines) {
        ArrayList<String> submitted_answers = new ArrayList<>();
        for (String assignment_line : assignment_lines) {
            if (assignment_line.contains("ANSWER: ")) {
                String[] answer = assignment_line.split(": ");
                submitted_answers.add(answer[1]);
            }
        }
        return submitted_answers;
    }

    // Method to get pending assignments for a particular student
    public ArrayList<Integer> get_course_assignments(ArrayList<Assignment> assignments, String course_code,
            int student_id) {
        System.out.println("Server: Total Assignments: " + assignments.size());
        ArrayList<Integer> pending_assignments = new ArrayList<>();
        for (Assignment assignment : assignments) {
            if (assignment.get_course_code().equals(course_code) && !assignment.get_student_submissions()
                    .contains(student_id)) {
                pending_assignments.add(assignment.get_id());
            }
        }
        return pending_assignments;
    }

    // Method to get assignment for a certain ID
    public Assignment get_assignment_by_id(ArrayList<Assignment> assignments, int id) {
        Assignment return_assignment = null;
        for (Assignment assignment : assignments) {
            if (assignment.get_id() == id) {
                return_assignment = assignment;
            }
        }
        return return_assignment;
    }
}
