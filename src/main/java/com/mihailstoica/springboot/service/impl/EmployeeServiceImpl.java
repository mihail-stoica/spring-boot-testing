package com.mihailstoica.springboot.service.impl;

import com.mihailstoica.springboot.exception.ResourceNotFoundException;
import com.mihailstoica.springboot.model.Employee;
import com.mihailstoica.springboot.repository.EmployeeRepository;
import com.mihailstoica.springboot.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee already exist with given email: " + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }
}