package com.example.employee.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
@TypeAlias("emp")
@Getter
@Setter
@ToString
public class Employee {
  @Id ObjectId id;
  String emailId;
  List<Schedule> schedule;
}
