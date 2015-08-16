package com.mongodb;

import spark.Spark;

/**
 * Created by vkjain on 8/4/15.
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {
        Spark.get("/", (req, res) -> "Hello World From Spark");
    }
}
