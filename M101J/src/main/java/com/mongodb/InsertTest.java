package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.Helpers.printJson;
import static java.util.Arrays.asList;

/**
 * Hello world!
 *
 */
public class InsertTest
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
        MongoCollection<Document> collection = db.getCollection("insertTest");

        collection.drop();
        Document smith = new Document("name", "Smith")
                .append("age",30)
                .append("profession", "programmer");
        Document jones = new Document("name", "Jones")
                .append("age",42)
                .append("profession", "hacker");
        collection.insertMany(asList(smith, jones));

//        collection.insertOne(new Document("x", 1));
//        Document doc = new Document()
//                .append("str", "Hello Mongo")
//                .append("int", 40)
//                .append("long", 1L)
//                .append("double", 2.0d)
//                .append("boolean", true)
//                .append("date", new Date())
//                .append("objectId", new ObjectId())
//                .append("null", null)
//                .append("embeddedDoc", new Document("X", 0))
//                .append("list", asList(1,2,3,4));
//
        Helpers.printJson(smith);


    }
}
