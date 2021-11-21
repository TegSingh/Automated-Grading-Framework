package Instructor;

public class Instructor {

    // Define the class variables
    int id;
    String password;
    String name;

    // Define the constructor
    public Instructor(int id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
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

}
