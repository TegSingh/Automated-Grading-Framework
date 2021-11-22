package Instructor;

public class Instructor {

    // Define the class variables
    int id;
    String password;
    String name;
    String course_code;

    // Define the constructor
    public Instructor(int id, String password, String name, String course_code) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.course_code = course_code;
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

    public String toString() {
        return "Instructor ID: " + this.id + " Name: " + this.name + " Course code: " + this.course_code;
    }

}
