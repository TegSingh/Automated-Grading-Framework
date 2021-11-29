package Assignment;

import java.util.ArrayList;

public class Assignment {

    // Initialize the class variables
    int id;
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> instructor_answers = new ArrayList<>();
    ArrayList<String> student_answers = new ArrayList<>();
    ArrayList<String[]> choices = new ArrayList<String[]>();
    String course_code;
    ArrayList<Integer> student_submissions = new ArrayList<>();

    // Define the default constructor
    public Assignment() {
        this.id = 0;
        this.questions = null;
        this.instructor_answers = null;
        this.student_answers = null;
        this.choices = null;
        this.course_code = "";
    }

    // Define the parameters constructor
    public Assignment(int id, ArrayList<String> questions, ArrayList<String> instructor_answers,
            ArrayList<String> student_answers, ArrayList<String[]> choices, String course_code) {
        this.id = id;
        this.questions = questions;
        this.instructor_answers = instructor_answers;
        this.student_answers = student_answers;
        this.choices = choices;
        this.course_code = course_code;
    }

    public int get_id() {
        return this.id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public ArrayList<String> get_questions() {
        return this.questions;
    }

    public void set_questions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public ArrayList<String> get_instructor_answers() {
        return this.instructor_answers;
    }

    public void set_instructor_answers(ArrayList<String> instructor_answers) {
        this.instructor_answers = instructor_answers;
    }

    public ArrayList<String> get_student_answers() {
        return this.student_answers;
    }

    public void set_student_answers(ArrayList<String> student_answers) {
        this.student_answers = student_answers;
    }

    public ArrayList<String[]> get_choices() {
        return this.choices;
    }

    public void set_choices(ArrayList<String[]> choices) {
        this.choices = choices;
    }

    public String get_course_code() {
        return this.course_code;
    }

    public void set_course_code(String course_code) {
        this.course_code = course_code;
    }

    public ArrayList<Integer> get_student_submissions() {
        return this.student_submissions;
    }

    public void set_student_submissions(ArrayList<Integer> student_submissions) {
        this.student_submissions = student_submissions;
    }

    // Method to return the Question as a string with the associated choices
    public String question_to_string(String question, String[] choices) {
        return question + "\n1. " + choices[0] + "\n2. " + choices[1] + "\n3. " + choices[2] + "\n4. " + choices[3];
    }

    // To string to get the assignment string for the instructor - potentially can
    // be written on a file
    public String instructor_to_string() {

        String assignment_to_string = "";
        assignment_to_string += "----------------------------------------\nAssignment ID: ";
        assignment_to_string += this.id;
        assignment_to_string += "\n";
        assignment_to_string += this.course_code;
        assignment_to_string += "\n";
        assignment_to_string += "----------------------------------------\n";

        // Loop through all questions in the assignment
        for (int i = 0; i < this.questions.size(); i++) {
            assignment_to_string += "\n";
            assignment_to_string += "QUESTION ";
            assignment_to_string += Integer.toString(i + 1);
            assignment_to_string += ": ";
            String question = this.questions.get(i);
            String[] mcq = this.choices.get(i);
            String question_string = question_to_string(question, mcq);
            assignment_to_string += question_string;
            assignment_to_string += "\n";
            assignment_to_string += "The correct option is: ";
            assignment_to_string += this.instructor_answers.get(i);
            assignment_to_string += "\n";
        }

        assignment_to_string += "----------------------------------------";

        return assignment_to_string;
    }

    // To string to get the assignment string for the student - potentially can be
    // written on a file
    public String student_to_string() {

        String assignment_to_string = "";
        assignment_to_string += "----------------------------------------\nAssignment ID: ";
        assignment_to_string += this.id;
        assignment_to_string += "\n";
        assignment_to_string += this.course_code;
        assignment_to_string += "\n";
        assignment_to_string += "----------------------------------------\n";

        // Loop through all questions in the assignment
        for (int i = 0; i < this.questions.size(); i++) {
            assignment_to_string += "\n";
            assignment_to_string += "QUESTION ";
            assignment_to_string += Integer.toString(i + 1);
            assignment_to_string += ": ";
            String question = this.questions.get(i);
            String[] mcq = this.choices.get(i);
            String question_string = question_to_string(question, mcq);
            assignment_to_string += question_string;
            assignment_to_string += "\n";

        }

        assignment_to_string += "----------------------------------------";

        return assignment_to_string;
    }

    public String submission_to_string() {
        String assignment_to_string = "";
        assignment_to_string += "----------------------------------------\nAssignment ID: ";
        assignment_to_string += this.id;
        assignment_to_string += "\n";
        assignment_to_string += this.course_code;
        assignment_to_string += "\n";
        assignment_to_string += "----------------------------------------\n";

        // Loop through all questions in the assignment
        for (int i = 0; i < this.questions.size(); i++) {
            assignment_to_string += "\n";
            assignment_to_string += "QUESTION ";
            assignment_to_string += Integer.toString(i + 1);
            assignment_to_string += ": ";
            String question = this.questions.get(i);
            String[] mcq = this.choices.get(i);
            String question_string = question_to_string(question, mcq);
            assignment_to_string += question_string;
            assignment_to_string += "\nANSWER: ";
            assignment_to_string += student_answers.get(i);
            assignment_to_string += "\n";

        }

        assignment_to_string += "----------------------------------------";

        return assignment_to_string;
    }

    public String toString() {
        String assignment_to_string = "";
        assignment_to_string += "----------------------------------------\nAssignment ID: ";
        assignment_to_string += this.id;
        assignment_to_string += "\n";
        assignment_to_string += this.course_code;
        assignment_to_string += "\n";
        assignment_to_string += "----------------------------------------\n";

        // Loop through all questions in the assignment
        for (int i = 0; i < this.questions.size(); i++) {
            assignment_to_string += "\n";
            assignment_to_string += "QUESTION ";
            assignment_to_string += Integer.toString(i + 1);
            assignment_to_string += ": ";
            String question = this.questions.get(i);
            String[] mcq = this.choices.get(i);
            String question_string = question_to_string(question, mcq);
            assignment_to_string += question_string;
            assignment_to_string += "\nThe correct option is: ";
            assignment_to_string += this.instructor_answers.get(i);
            assignment_to_string += "\n";
            assignment_to_string += "ANSWER: ";
            assignment_to_string += student_answers.get(i);
            assignment_to_string += "\n";

        }

        assignment_to_string += "----------------------------------------";

        return assignment_to_string;
    }
}
