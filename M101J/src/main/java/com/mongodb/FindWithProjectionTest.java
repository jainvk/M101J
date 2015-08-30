package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Random;

import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.Helpers.printJson;
import static com.mongodb.client.model.Filters.*;
import static java.util.Arrays.asList;

/**
 * Hello world!
 *
 */
public class FindWithProjectionTest
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

        Bson filter = and(eq("X", 0), gt("Y", 10), lt("Y", 90));
//        Bson projection = new Document("X", 0).append("_id", 0);
//        Bson projection = new Document("Y", 1).append("_id", 0);

//        Bson projection = Projections.exclude("X","_id");
        Bson projection = Projections.fields(Projections.include("Y"), Projections.excludeId());

        ArrayList<Document> documents = collection.find(filter).projection(projection).into(newArrayList());
        for (Document document : documents) {
            printJson(document);
        }

        System.out.println("Count: ");
        long count = collection.count(filter);
        System.out.println(count);

    }
}
