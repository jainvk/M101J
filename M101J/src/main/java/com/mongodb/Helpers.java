package com.mongodb;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;

/**
 * Created by vkjain on 8/16/15.
 */
public class Helpers {
    public static void printJson(Document doc) {

        JsonWriter writer = new JsonWriter(new StringWriter(),new JsonWriterSettings(JsonMode.SHELL,true));
        new DocumentCodec().encode(writer,doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        System.out.println(writer.getWriter());
        System.out.flush();


    }
}
