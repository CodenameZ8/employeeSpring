package com.example.employee.repository;

import com.mongodb.client.*;
import java.util.*;
import org.bson.Document;

public class ScheduleQueryRepository {

  public List<Document> getByDate(Date date) {
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase database = mongoClient.getDatabase("test");
    MongoCollection<Document> collection = database.getCollection("employee");

    AggregateIterable<Document> result =
        collection.aggregate(
            Arrays.asList(
                new Document(
                    "$match",
                    new Document("schedule.startDateObj", new Document("$lte", date))
                        .append("schedule.endDateObj", new Document("$gte", date))),
                new Document("$sort", new Document("_id", 1L)),
                new Document(
                    "$project",
                    new Document("_id", 0L)
                        .append("schedule.time", 1L)
                        .append("schedule.duration", 1L)
                        .append("schedule.startDate", 1L)
                        .append("schedule.endDate", 1L))));

    Iterator<Document> itr = result.iterator();
    List<Document> documents = new ArrayList<>();
    while ((itr.hasNext())) {
      documents.add(itr.next());
    }

    return documents;
  }
}
