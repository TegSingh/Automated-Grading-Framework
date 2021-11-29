package Instructor;

import java.util.ArrayList;

public class Instructor {

    // Define the class variables
    int id;
    String password;
    String name;
    String course_code;
    boolean logged_in;
    ArrayList<Integer> posted_assignments = new ArrayList<>();

    // Define the constructor
    public Instructor(int id, String password, String name, String course_code, boolean logged_in) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.course_code = course_code;
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

    public String get_course_code() {
        return this.course_code;
    }

    public void set_course_code(String course_code) {
        this.course_code = course_code;
    }

    public boolean is_logged_in() {
        return this.logged_in;
    }

    public void set_logged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public ArrayList<Integer> get_posted_assignments() {
        return this.posted_assignments;
    }

    public void set_posted_assignments(ArrayList<Integer> posted_assignments) {
        this.posted_assignments = posted_assignments;
    }

    public String toString() {
        return "Instructor ID: " + this.id + " Name: " + this.name + " Course code: " + this.course_code
                + " Logged in: " + logged_in;
    }

    // To string method for file writing
    public String toStringFile() {
        return this.id + ", " + this.password + ", " + this.name + ", " + this.course_code + ", " + this.logged_in;
    }

}
