package com.mihailstoica.springboot.service;

import com.mihailstoica.springboot.exception.ResourceNotFoundException;
import com.mihailstoica.springboot.model.Employee;
import com.mihailstoica.springboot.repository.EmployeeRepository;
import com.mihailstoica.springboot.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        this.employee = Employee.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@xyz,com")
                .build();
    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        //given - precondition or setup

        //stub method employeeRepository.findByEmail()
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        //stub method employeeRepository.save()
        given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenThrowsResourceNotFoundException() {

        //given - precondition or setup

        //stub method employeeRepository.findByEmail()
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        //when - action or behaviour that we are going to test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));

        //then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    //JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeeRepository_whenGetAllEmployees_thenReturnEmployeesList() {

        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,com")
                .build();
        //stub method employeeRepository.findAll()
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        //when - action or behaviour that we are going to test
        List<Employee> savedAllEmployee = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(savedAllEmployee).isNotNull();
        assertThat(savedAllEmployee.size()).isEqualTo(2);
        assertThat(savedAllEmployee).isEqualTo(employeeRepository.findAll());
    }

    //JUnit test for getAllEmployees method, negative scenario
    @DisplayName("JUnit test for getAllEmployees method, negative scenario")
    @Test
    public void givenEmptyEmployeeRepository_whenGetAllEmployees_thenReturnEmptyEmployeesList() {

        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,com")
                .build();
        //stub method employeeRepository.findAll()
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or behaviour that we are going to test
        List<Employee> savedAllEmployee = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(savedAllEmployee).isEmpty();
    }

    //JUnit test for getEmployeeById
    @DisplayName("JUnit test for getEmployeeById")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {

        //given - precondition or setup
        long id = 1L;
        //stub method employeeRepository.findById()
        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

    //JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("john.doe@xyz,xyz");
        employee.setFirstName("John");

        //when - action or behaviour that we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee).isEqualTo(employee);
    }

    //JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {

        //given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when - action or behaviour that we are going to test
        employeeService.deleteEmployee(employeeId);

        //then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}
