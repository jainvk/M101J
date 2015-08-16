package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created by vkjain on 8/4/15.
 */
public class HelloWorldFreemarkerStyle {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");
        try {
            Template helloTemplate = configuration.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> helloMap = newHashMap();
            helloMap.put("name", "Freemarker");
            helloTemplate.process(helloMap, writer);
            System.out.println(writer);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
