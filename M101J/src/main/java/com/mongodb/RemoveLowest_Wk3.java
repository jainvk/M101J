package com.mongodb;

/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public class RemoveLowest_Wk3 {
    public static void main(final String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase schoolDB = client.getDatabase("school");
        MongoCollection<Document> students = schoolDB.getCollection("students");

        MongoCursor<Document> cursor = students.find().iterator();

        try {
            while (cursor.hasNext()) {
                Document student = cursor.next();
                List<Document> scores = (List<Document>) student.get("scores");
                // Now find the lowest homework score.
                Document minScoreObj = null;
                double minScore = Double.MAX_VALUE;  // Minimum score value.
                for (Document scoreDoc : scores) {
                    Double score = scoreDoc.getDouble("score");
                    String type = scoreDoc.getString("type");
                    if (type.equals("homework") && score < minScore) {
                        minScore = score;
                        minScoreObj = scoreDoc;
                    }
                }
                // Remove the lowest score.
                if (minScoreObj != null) {
                    scores.remove(minScoreObj);   // remove the lowest
                }
                // replace the scores array for the student
                students.updateOne(eq("_id", student.get("_id")),
                        new Document("$set", new Document("scores", scores)));

//                students.replaceOne(eq("_id", student.get("_id")), student);

            }

        } finally {
            cursor.close();
        }
        client.close();
    }
}
