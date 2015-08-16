package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

import static java.util.Arrays.asList;

/**
 * Hello world!
 *
 */
public class App 
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
        MongoDatabase db = mongoClient.getDatabase("test").withReadPreference(ReadPreference.secondary());
        MongoCollection<BsonDocument> collection = db.getCollection("test", BsonDocument.class);

        Document doc = new Document()
                .append("str", "Hello Mongo")
                .append("int", 40)
                .append("long", 1L)
                .append("double", 2.0d)
                .append("boolean", true)
                .append("date", new Date())
                .append("objectId", new ObjectId())
                .append("null", null)
                .append("embeddedDoc", new Document("X", 0))
                .append("list", asList(1,2,3,4));

        Helpers.printJson(doc);




    }
}
