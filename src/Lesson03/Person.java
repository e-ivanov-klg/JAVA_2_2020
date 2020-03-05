package Lesson03;

public class Person {
    private String name;
    private String phoneNumber;

    Person (String name, String phone){
        this.name   = name;
        this.phoneNumber = phone;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " => " + phoneNumber;
    }
}
