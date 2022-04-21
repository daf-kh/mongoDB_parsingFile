package src;

import java.util.ArrayList;

public class Student {
    private final String name;
    private final int age;
    private final ArrayList<String> courses;

    public Student(String name, int age, ArrayList<String> cources) {
        this.name = name;
        this.age = age;
        this.courses = cources;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

}
