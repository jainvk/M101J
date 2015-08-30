package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.Helpers.printJson;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.*;
import static java.util.Arrays.asList;
import static java.util.Arrays.equals;

/**
 * Hello world!
 */
public class FindWithSortSkipLimitTest {
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

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                collection.insertOne(new Document("i", i).append("j", j));
            }
        }

//        Bson filter = and(eq("X", 0), gt("Y", 10), lt("Y", 90));
//        Bson projection = new Document("X", 0).append("_id", 0);
//        Bson projection = new Document("Y", 1).append("_id", 0);

//        Bson projection = Projections.exclude("X","_id");
        Bson projection = Projections.fields(Projections.include("i", "j"), Projections.excludeId());
//        Bson sort = new Document("i", -1).append("j", -1);

        Bson sort = orderBy(ascending("i"), descending("j"));

        ArrayList<Document> documents = collection.find().sort(sort).skip(20).limit(50  ).projection(projection).into(newArrayList());
        for (Document document : documents) {
            printJson(document);
        }

        System.out.println("Count: ");
        long count = collection.count();
        System.out.println(count);




    }
}
