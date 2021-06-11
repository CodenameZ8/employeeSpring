package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.model.Schedule;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.ScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

  @Autowired EmployeeRepository employeeRepository;
  @Autowired EmployeeService employeeService;
  @Autowired ScheduleService scheduleService;

  @PostMapping
  public Employee saveEmployee(@RequestBody String json) throws JsonProcessingException {
    Employee employee = new ObjectMapper().readValue(json, Employee.class);
    return employeeRepository.insert(employee);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployee(@PathVariable ObjectId id) {
    return employeeService.getEmployeeApi(id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEmployee(@PathVariable ObjectId id) {
    employeeRepository.deleteById(id.toString());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Employee> updateUser(
      @RequestBody Employee employee, @PathVariable ObjectId id) {
    return employeeService.updateEmployeeApi(employee, id);
  }

  @PostMapping("/{id}/schedule")
  public ResponseEntity saveSchedule(@PathVariable ObjectId id, @RequestBody Schedule schedule)
      throws JsonProcessingException {
    return scheduleService.insertSchedule(id, schedule);
  }

  @PutMapping("/{employeeId}/schedule/{scheduleId}")
  public ResponseEntity saveSchedule(
      @PathVariable("employeeId") ObjectId employeeId,
      @PathVariable("scheduleId") String scheduleId,
      @RequestBody Schedule schedule)
      throws JsonProcessingException, ParseException {
    return scheduleService.modifySchedule(employeeId, scheduleId, schedule);
  }

  @DeleteMapping("{employeeId}/schedule/{scheduleId}")
  public ResponseEntity deleteSchedule(
      @PathVariable("employeeId") ObjectId employeeId,
      @PathVariable("scheduleId") String scheduleId,
      @RequestBody Schedule schedule) {
    return scheduleService.deleteSchedule(employeeId, scheduleId, schedule);
  }

  @GetMapping("/schedule")
  List<Document> getScheduleDate(@RequestParam(name = "date", required = true) String date)
      throws ParseException {
    return scheduleService.getSchudleDate(date);
  }
}
