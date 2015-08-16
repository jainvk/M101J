package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Spark;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static spark.Spark.halt;

/**
 * Created by vkjain on 8/4/15.
 */
public class SparkFormHandler {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(SparkFormHandler.class, "/");
        StringWriter writer = new StringWriter();

        Spark.get("/", (req, res) ->
        {
            try {
                Template fruitPickerTemplate = configuration.getTemplate("fruitPicker.ftl");

                Map<String, Object> fruitsMap = newHashMap();
                fruitsMap.put("fruits", Arrays.asList("apple", "orange", "banana", "peach"));
                fruitPickerTemplate.process(fruitsMap, writer);


            } catch (Exception e) {
                halt(500);
                e.printStackTrace();
            }
            return writer;
        });

        Spark.post("/favorite_fruit", (req, res) ->
        {
            String fruit = req.queryParams("fruit");
            if(fruit == null) {
                return "why don't you pick one?";
            } else {
                return "Your favorite fruit is: " + fruit;
            }
            
        });

        Spark.get("/echo/:thing", (req, res) -> "Hello World From Spark: " + req.params(":thing"));
    }
}
