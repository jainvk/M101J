package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.Helpers.printJson;
import static java.util.Arrays.asList;

/**
 * Hello world!
 *
 */
public class FindTest
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://localhost:27017");
        MongoClientOptions options =
                MongoClientOptions.builder()
                        .connectionsPerHost(10)
                        .build();

        MongoClient mongoClient = new MongoClient(asList(new ServerAddress("localhost", 27017)), options);
        MongoDatabase db = mongoClient.getDatabase("course").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("findTest");

        collection.drop();

        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document("X", i));
        }


        System.out.printf("find one");

        printJson(collection.find().first());
        System.out.printf("find all with into: ");

        ArrayList<Document> documents = collection.find().into(newArrayList());
        for (Document document : documents) {
            printJson(document);
        }

        System.out.printf("find all with iteration: ");
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document next = cursor.next();
                printJson(next);
            }
        } finally {
            cursor.close();
        }

        System.out.println("Count: ");
        long count = collection.count();
        System.out.println(count);

    }
}
