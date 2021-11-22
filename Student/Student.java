package Student;

public class Student {

    // Define the class variables
    int id;
    String password;
    String name;
    double grade;
    int instructor_id;

    // Define the constructor
    public Student(int id, String password, String name, double grade, int instructor_id) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.grade = grade;
        this.instructor_id = instructor_id;
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

    // Set the to string method
    public String toString() {
        return "Student ID: " + this.id + " Name: " + this.name + " Grade: " + this.grade + " Instructor: "
                + this.instructor_id;
    }
}
