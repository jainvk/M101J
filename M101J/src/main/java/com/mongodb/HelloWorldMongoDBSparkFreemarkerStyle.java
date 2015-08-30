package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Spark;

import java.io.StringWriter;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.mongodb.Helpers.printJson;
import static java.util.Arrays.asList;
import static spark.Spark.halt;

/**
 * Created by vkjain on 8/4/15.
 */
public class HelloWorldMongoDBSparkFreemarkerStyle {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldMongoDBSparkFreemarkerStyle.class, "/");

        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://localhost:27017");
        MongoClientOptions options =
                MongoClientOptions.builder()
                        .connectionsPerHost(10)
                        .build();

        MongoClient mongoClient = new MongoClient(asList(new ServerAddress("localhost", 27017)), options);
        MongoDatabase db = mongoClient.getDatabase("course").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("hello");

        collection.drop();

            collection.insertOne(new Document("name", "HelloWorldMongoDBSparkFreemarkerStyle"));


        StringWriter writer = new StringWriter();

        Spark.get("/", (req, res) ->
        {
            try {
                Template helloTemplate = configuration.getTemplate("hello.ftl");

//                Map<String, Object> helloMap = newHashMap();
//                helloMap.put("name", "Freemarker");
//                helloTemplate.process(helloMap, writer);


                Document doc = collection.find().first();
                printJson(doc);
                helloTemplate.process(doc, writer);


            } catch (Exception e) {
                halt(500);
                e.printStackTrace();
            }
            return writer;
        });

        Spark.get("/echo/:thing", (req, res) -> "Hello World From Spark: " + req.params(":thing"));
    }
}
