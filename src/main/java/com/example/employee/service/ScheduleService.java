package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.model.Schedule;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.ScheduleQueryRepository;
import com.example.employee.repository.ScheduleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
  @Autowired EmployeeRepository employeeRepository;
  @Autowired ScheduleRepository scheduleRepository;

  public ResponseEntity insertSchedule(ObjectId id, Schedule schedule)
      throws JsonProcessingException {
    Optional<Employee> employeeOp = employeeRepository.findById(id.toString());
    if (!employeeOp.isPresent()) {
      return new ResponseEntity<String>("Employee not found", HttpStatus.NOT_FOUND);
    }
    Employee employee = employeeOp.get();
    employee.getSchedule().add(schedule);
    employeeRepository.save(employee);
    return new ResponseEntity<Employee>(employee, HttpStatus.OK);
  }

  public ResponseEntity modifySchedule(ObjectId employeeId, String scheduleId, Schedule schedule)
      throws ParseException {
    Optional<Employee> employeeOp = employeeRepository.findById(employeeId.toString());
    if (!employeeOp.isPresent()) {
      return new ResponseEntity<String>("Employee not found", HttpStatus.NOT_FOUND);
    }
    Employee employee = employeeOp.get();
    List<Schedule> schedules = employee.getSchedule();
    ListIterator<Schedule> iterator = schedules.listIterator();
    Schedule tempSchedule = null;
    while (iterator.hasNext()) {
      tempSchedule = iterator.next();
      if (tempSchedule.getId().equals(scheduleId)) {

        if (schedule.getEndDate() != null) {
          tempSchedule.setEndDate(schedule.getEndDate());
        }
        if (schedule.getStartDate() != null) {
          tempSchedule.setStartDate(schedule.getStartDate());
        }
        if (schedule.getDuration() != null) {
          tempSchedule.setDuration(schedule.getDuration());
        }
        if (schedule.getTime() != null) {
          tempSchedule.setTime(schedule.getTime());
        }
        if (schedule.getRepeat() != null) {
          tempSchedule.setRepeat(schedule.getRepeat());
        }
        if (schedule.getFrequency() != null) {
          tempSchedule.setFrequency(schedule.getFrequency().toString());
        }
        break;
      }
    }

    if (tempSchedule == null) {
      return new ResponseEntity<String>("Schedule not found", HttpStatus.NOT_FOUND);
    }

    employeeRepository.save(employee);
    return new ResponseEntity<Employee>(employee, HttpStatus.OK);
  }

  public ResponseEntity deleteSchedule(ObjectId employeeId, String scheduleId, Schedule schedule) {
    Optional<Employee> employeeOp = employeeRepository.findById(employeeId.toString());
    if (!employeeOp.isPresent()) {
      return new ResponseEntity<String>("Employee not found", HttpStatus.NOT_FOUND);
    }
    Employee employee = employeeOp.get();
    List<Schedule> schedules = employee.getSchedule();
    ListIterator<Schedule> iterator = schedules.listIterator();
    Schedule tempSchedule;
    while (iterator.hasNext()) {

      tempSchedule = iterator.next();
      if (tempSchedule.getId().equals(scheduleId)) {
        iterator.remove();
      }
    }
    employeeRepository.save(employee);
    return new ResponseEntity<Employee>(employee, HttpStatus.OK);
  }

  public List<Document> getSchudleDate(String currentDate) throws ParseException {
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    Date date = df.parse(currentDate);
    return new ScheduleQueryRepository().getByDate(date);
  }
}
