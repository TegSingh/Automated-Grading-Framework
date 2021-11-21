package Student;

public class Student {

    // Define the class variables
    int id;
    String password;
    String name;
    double grade;

    // Define the constructor
    public Student(int id, String password, String name, double grade) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.grade = grade;
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
}
