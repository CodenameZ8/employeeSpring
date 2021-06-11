package com.example.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import lombok.ToString;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@TypeAlias("schedule")
@ToString
@Document
public class Schedule {

  public enum Frequency {
    Weekdays,
    Daily,
    Weekly,
    Monthly;
  }

  String id = UUID.randomUUID().toString();

  @JsonIgnore Date startDateObj;
  @JsonIgnore Date endDateObj;

  String time;
  Integer duration;
  Boolean repeat;
  Frequency frequency;

  String startDate;
  String endDate;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Boolean getRepeat() {
    return repeat;
  }

  public void setRepeat(Boolean repeat) {
    this.repeat = repeat;
  }

  public Frequency getFrequency() {
    return frequency;
  }

  public void setFrequency(String frequencyString) {
    Frequency frequency = Frequency.valueOf(frequencyString);
    this.frequency = frequency;
  }

  public void setStartDateObj(String startDate) throws ParseException {
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    this.startDateObj = df.parse(startDate);
  }

  public void setEndDateObj(String endDate) throws ParseException {
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    this.endDateObj = df.parse(endDate);
  }

  public Date getStartDateObj() {
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    return startDateObj;
  }

  public Date getEndDateObj() {
    return endDateObj;
  }

  public void setStartDateObj(Date startDate) {
    this.startDateObj = startDate;
  }

  public void setEndDateObj(Date endDate) {
    this.endDateObj = endDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) throws ParseException {
    this.startDate = startDate;
    setStartDateObj(startDate);
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) throws ParseException {
    this.endDate = endDate;
    setEndDateObj(endDate);
  }
}
