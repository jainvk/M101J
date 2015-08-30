package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.Helpers.printJson;
import static com.mongodb.client.model.Filters.gte;
import static java.util.Arrays.asList;

/**
 * Hello world!
 */
public class DeleteTest {
    public static void main(String[] args) {

        System.out.println("Hello World!");
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://localhost:27017");
        MongoClientOptions options =
                MongoClientOptions.builder()
                        .connectionsPerHost(10)
                        .build();

        MongoClient mongoClient = new MongoClient(asList(new ServerAddress("localhost", 27017)), options);
        MongoDatabase db = mongoClient.getDatabase("course").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("findWithSortTest");

        collection.drop();

        for (int i = 0; i < 9; i++) {
            collection.insertOne(new Document("i", i).append("_id", i));
        }


        collection.deleteMany(gte("_id", 5));

        ArrayList<Document> documents = collection.find().into(newArrayList());
        for (Document document : documents) {
            printJson(document);
        }

    }
}
