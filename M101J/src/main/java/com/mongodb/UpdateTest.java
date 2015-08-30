package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.operation.UpdateOperation;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.Helpers.printJson;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Sorts.*;
import static java.util.Arrays.asList;

/**
 * Hello world!
 */
public class UpdateTest {
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


//        collection.replaceOne(eq("i", 5), new Document("x", 20).append("update", true));
//        collection.updateOne(eq("i", 5), new Document("$set", new Document("x", 40)));
//        collection.updateOne(eq("_id", 9), new Document("$set", new Document("x", 40)), new UpdateOptions().upsert(true));
        collection.updateMany(gte("_id", 5), new Document("$inc", new Document("i", 40)));

        ArrayList<Document> documents = collection.find().into(newArrayList());
        for (Document document : documents) {
            printJson(document);
        }

    }
}
