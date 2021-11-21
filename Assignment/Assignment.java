package Assignment;

import java.util.ArrayList;

public class Assignment {

    // Initialize the class variables
    String id;
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();
    String[][] choices;

    // Define the constructor
    public Assignment(String id, ArrayList<String> questions, ArrayList<String> answers, String[][] choices) {
        this.id = id;
        this.questions = questions;
        this.answers = answers;
        this.choices = choices;
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

    public ArrayList<String> answers() {
        return this.answers;
    }

    public void set_answers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String[][] get_choices() {
        return this.choices;
    }

    public void set_choices(String[][] choices) {
        this.choices = choices;
    }
}
