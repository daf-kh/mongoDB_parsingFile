package src;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

import java.io.IOException;
import java.util.function.Consumer;


public class Main {

    private static final String LIST_CSV = "src/main/resources/mongo.csv";

    public static void main(String[] args) throws IOException {
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );

        MongoDatabase database = mongoClient.getDatabase("db");

        MongoCollection<Document> collection = database.getCollection("studentsCollection");

        collection.drop();

        //Парсим документ и на каждую строчку со студентом создаем отдельный документ для добавления в коллекцию
        for(Student student : ParsingFile.loadStudentsFromFile(LIST_CSV)) {
            Document studentDocument = new Document()
                    .append("name", student.getName())
                    .append("age", student.getAge())
                    .append("courses", student.getCourses());

            collection.insertOne(studentDocument);
        }


        System.out.println("Количество студентов: " + collection.countDocuments() + "человек\n");

        BsonDocument query = BsonDocument.parse("{age: {$gt: 40}}");
        System.out.println("Студенты старше 40: " + collection.countDocuments(query) + " человек\n");
        //Чтобы вывод выглядел красиво, запрашиваю все значения из документов по отдельности
        collection.find(query).forEach((Consumer<Document>) document ->
                System.out.println(document.get("name") + ", " + document.get("age") + ", " + document.get("courses")));

        System.out.println("\nСамый молодой студент: " );
        collection.find().sort(ascending("age")).limit(1).forEach((Consumer<Document>) document ->
                System.out.println(document.get("name") + ", " + document.get("age") + ", " + document.get("courses")));

        System.out.println("\nСписок курсов двух самых взрослых студентов (они ровесники): ");
        collection.find().sort(descending("age")).limit(2).forEach((Consumer<Document>) document ->
                System.out.println(document.get("courses")));

    }
}
