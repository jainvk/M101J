package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Random;

import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.Helpers.printJson;
import static java.util.Arrays.asList;

/**
 * Hello world!
 *
 */
public class FindWithFilterTest
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
        MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();

        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document("X", new Random().nextInt(2)).append("Y", new Random().nextInt(100)));
        }


        Bson filter = new Document("X", 0).append("Y", new Document("$gt", 10).append("$lFindWithFilterTestt", 90));


        ArrayList<Document> documents = collection.find(filter).into(newArrayList());
        for (Document document : documents) {
            printJson(document);
        }



        System.out.println("Count: ");
        long count = collection.count(filter);
        System.out.println(count);

    }
}
