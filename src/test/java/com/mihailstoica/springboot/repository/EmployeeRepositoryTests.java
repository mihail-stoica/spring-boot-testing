package com.mihailstoica.springboot.repository;

import com.mihailstoica.springboot.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    //JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,com")
                .build();

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    //JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenReturnEmployeeList() {

        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@xyz,com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
        assertThat(employee1).isEqualTo(employeeList.get(0));
        assertThat(employee2).isEqualTo(employeeList.get(1));
    }

    //JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,com")
                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject() {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,com")
                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,com")
                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("john_doe@xyz,xyz");
        savedEmployee.setFirstName("Jon");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("john_doe@xyz,xyz");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Jon");
    }

    //JUnit test for delete employee
    @DisplayName("JUnit test for delete employee")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,com")
                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        //employeeRepository.deleteById(employee.getId());
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then - verify the output
        //assertThat(employeeRepository.count()).isEqualTo(0);
        assertThat(employeeOptional).isEmpty();
    }

}
