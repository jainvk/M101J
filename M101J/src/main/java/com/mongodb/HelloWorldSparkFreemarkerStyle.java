package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Spark;

import java.io.StringWriter;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static spark.Spark.halt;

/**
 * Created by vkjain on 8/4/15.
 */
public class HelloWorldSparkFreemarkerStyle {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");
        StringWriter writer = new StringWriter();

        Spark.get("/", (req, res) ->
        {
            try {
                Template helloTemplate = configuration.getTemplate("hello.ftl");

                Map<String, Object> helloMap = newHashMap();
                helloMap.put("name", "Freemarker");
                helloTemplate.process(helloMap, writer);


            } catch (Exception e) {
                halt(500);
                e.printStackTrace();
            }
            return writer;
        });

        Spark.get("/echo/:thing", (req, res) -> "Hello World From Spark: " + req.params(":thing"));
    }
}
