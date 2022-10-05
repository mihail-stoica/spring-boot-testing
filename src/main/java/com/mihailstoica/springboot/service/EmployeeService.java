package com.mihailstoica.springboot.service;

import com.mihailstoica.springboot.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();
}
