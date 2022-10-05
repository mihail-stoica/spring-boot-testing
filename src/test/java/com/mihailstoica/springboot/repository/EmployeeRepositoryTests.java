package com.mihailstoica.springboot.repository;

import com.mihailstoica.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
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
    private Employee employee;
    @BeforeEach
    public void setup() {
        this.employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,com")
                .build();
        employeeRepository.save(this.employee);
    }

    //JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //given - precondition or setup

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
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@xyz,com")
                .build();
        employeeRepository.save(employee1);

        //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
        assertThat(employee).isEqualTo(employeeList.get(0));
        assertThat(employee1).isEqualTo(employeeList.get(1));
    }

    //JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

        //given - precondition or setup

        //when - action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).orElseThrow();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject() {

        //given - precondition or setup

        //when - action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).orElseThrow();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given - precondition or setup

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).orElseThrow();
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

        //when - action or behaviour that we are going to test
        //employeeRepository.deleteById(employee.getId());
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then - verify the output
        //assertThat(employeeRepository.count()).isEqualTo(0);
        assertThat(employeeOptional).isEmpty();
    }

    //JUnit test for get employee by first and last name, JPQL custom query with index parameters
    @DisplayName("JUnit test for get employee by first and last name")
    @Test
    public void givenFirsNameAndLastName_whenFindByFirstNameAndLastName_thenReturnEmployeeObject() {

        //given - precondition or setup
        String firstName = "John";
        String lastName = "Doe";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByFirstNameAndLastName(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

    //JUnit test for get employee by first name and email, JPQL custom query with named parameters
    @DisplayName("JUnit test for get employee by first name and email")
    @Test
    public void givenFirstNameAndEmail_whenFindByFirstNameAndEmail_thenReturnEmployeeObject() {

        //given - precondition or setup
        String firstName = "John";
        String email = "john.doe@xyz,com";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByFirstNameAndEmail(firstName, email);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

    //JUnit test for get employee by last name and email, custom query using native SQL with indexed parameters
    @DisplayName("JUnit test for get employee by last name and email")
    @Test
    public void givenLastNameAndEmail_whenFindByLastNameAndEmail_thenReturnEmployeeObject() {

        //given - precondition or setup
        String lastName = employee.getLastName();
        String email = employee.getEmail();

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByLastNameAndEmail(lastName, email);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

    //JUnit test for get employee by first name, last name and email, custom query using native SQL with named param
    @DisplayName("JUnit test for get employee by first name, last name and email")
    @Test
    public void givenFirstNameAndLastNameAndEmail_whenFindByFirstNameAndLastNameAndEmail_thenReturnEmployeeObject() {

        //given - precondition or setup

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByFirstNameAndLastNameAndEmail(employee.getFirstName(),
                employee.getLastName(), employee.getEmail());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }


}
