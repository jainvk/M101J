package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.mongodb.Helpers.printJson;
import static com.mongodb.client.model.Sorts.*;
import static java.util.Arrays.asList;

/**
 * Hello world!
 */
public class Homework2_3 {
    public static void main(String[] args) {



        System.out.println("Hello World!");
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://localhost:27017");
        MongoClientOptions options =
                MongoClientOptions.builder()
                        .connectionsPerHost(10)
                        .build();

        MongoClient mongoClient = new MongoClient(asList(new ServerAddress("localhost", 27017)), options);
        MongoDatabase db = mongoClient.getDatabase("students").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("grades");

//        Bson filter = and(eq("X", 0), gt("Y", 10), lt("Y", 90));
//        Bson projection = new Document("X", 0).append("_id", 0);
//        Bson projection = new Document("Y", 1).append("_id", 0);

//        Bson projection = Projections.exclude("X","_id");
        Bson projection = Projections.fields(Projections.include("student_id", "score"));
//        Bson sort = new Document("i", -1).append("j", -1);

        Bson sort = orderBy(ascending("student_id"), ascending("score"));

        ArrayList<Document> documents = collection.find().sort(sort).projection(projection).into(newArrayList());
        Set<Integer> allStudents = newHashSet();

        documents.forEach(document -> {
            Integer student_id = (Integer) document.get("student_id");
            if (!allStudents.contains(student_id)) {
                collection.deleteOne(document);
                allStudents.add(student_id);
            }
        });



        ArrayList<Document> remainingDocuments = collection.find().sort(sort).projection(projection).into(newArrayList());
        for (Document document : remainingDocuments) {
            printJson(document);
        }

        System.out.println("Count: ");
        long count = collection.count();
        System.out.println(count);




    }
}
