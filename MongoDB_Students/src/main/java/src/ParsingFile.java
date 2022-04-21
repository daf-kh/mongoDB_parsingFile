package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParsingFile {

    public static List<Student> loadStudentsFromFile(String path) throws IOException {

        List<String> studentsFromFile = Files.readAllLines(Paths.get(path));
        List<Student> students = new ArrayList<>();

        for(String studentLine : studentsFromFile) {

            //Вытаскиваем фрагменты строчки по разделяющим запятым
            String[] fragments = studentLine.split(",");

            String name = fragments[0];
            int age = Integer.parseInt(fragments[1]);

            ArrayList<String> courses = new ArrayList<>();
            //Все, что после возраста, и за исключением кавычек - список курсов
            for(int i=2; i < fragments.length; i++) {
                courses.add(fragments[i].replace("\"", ""));
            }

            students.add(new Student(name, age, courses));
        }

        return students;
    }

}
