package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
  @Autowired private EmployeeRepository employeeRepository;

  public ResponseEntity<Employee> getEmployeeApi(ObjectId id) {
    Employee employee = null;

    Optional<Employee> opEmployee = employeeRepository.findById(id.toString());
    if (opEmployee.isPresent()) {
      return new ResponseEntity<>(opEmployee.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  public Optional<Employee> getEmployee(ObjectId id) {
    return employeeRepository.findById(id.toString());
  }

  public ResponseEntity<Employee> updateEmployeeApi(Employee employee, ObjectId id) {
    Optional<Employee> opEmployee = employeeRepository.findById(id.toString());
    if (opEmployee.isPresent()) {
      employee.setId(id);
      return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
