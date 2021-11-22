package Assignment;

import java.util.ArrayList;

public class Assignment {

    // Initialize the class variables
    String id;
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> instructor_answers = new ArrayList<>();
    ArrayList<String> student_answers = new ArrayList<>();
    String[][] choices;
    String course_code;

    // Define the constructor
    public Assignment(String id, ArrayList<String> questions, ArrayList<String> instructor_answers,
            ArrayList<String> student_answers, String[][] choices, String course_code) {
        this.id = id;
        this.questions = questions;
        this.instructor_answers = instructor_answers;
        this.student_answers = student_answers;
        this.choices = choices;
        this.course_code = course_code;
    }

    public String get_id() {
        return this.id;
    }

    public void set_id(String id) {
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

    public void set_answers(ArrayList<String> instructor_answers) {
        this.instructor_answers = instructor_answers;
    }

    public ArrayList<String> get_student_answers() {
        return this.student_answers;
    }

    public void set_student_answers(ArrayList<String> student_answers) {
        this.student_answers = student_answers;
    }

    public String[][] get_choices() {
        return this.choices;
    }

    public void set_choices(String[][] choices) {
        this.choices = choices;
    }

    public String get_course_code() {
        return this.course_code;
    }

    public void set_course_code(String course_code) {
        this.course_code = course_code;
    }
}
