package Student;

import java.util.ArrayList;

public class Student {

    // Define the class variables
    int id;
    String password;
    String name;
    double grade;
    int instructor_id;
    boolean logged_in;
    ArrayList<Integer> submitted_assignments = new ArrayList<>();

    // Define the constructor
    public Student(int id, String password, String name, double grade, int instructor_id, boolean logged_in) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.grade = grade;
        this.instructor_id = instructor_id;
        this.logged_in = logged_in;
    }

    // Define getter and setters
    public int get_id() {
        return this.id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public String get_password() {
        return this.password;
    }

    public void set_password(String password) {
        this.password = password;
    }

    public String get_name() {
        return this.name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public double get_grade() {
        return this.grade;
    }

    public void set_grade(double grade) {
        this.grade = grade;
    }

    public int get_instructor_id() {
        return this.instructor_id;
    }

    public void set_instructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public boolean is_logged_in() {
        return this.logged_in;
    }

    public void set_logged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public ArrayList<Integer> get_submitted_assignments() {
        return this.submitted_assignments;
    }

    public void set_submitted_assignmets(ArrayList<Integer> submitted_assignments) {
        this.submitted_assignments = submitted_assignments;
    }

    // Set the to string method
    public String toString() {
        return "Student ID: " + this.id + " Name: " + this.name + " Grade: " + this.grade + " Instructor: "
                + this.instructor_id + " Logged in: " + logged_in;
    }

    // To string method for file writing
    public String toStringFile() {
        return this.id + ", " + this.password + ", " + this.name + ", " + this.grade + ", " + this.instructor_id + ", "
                + this.logged_in;
    }
}
