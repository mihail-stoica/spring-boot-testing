package com.mihailstoica.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihailstoica.springboot.model.Employee;
import com.mihailstoica.springboot.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("JUnit test for createEmployee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,xyz")
                .build();
        //stub method employeeService.saveEmployee()
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @DisplayName("JUnit test for getAllEmployees()")
    @Test
    public void givenListOfEmployee_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {

        //given - precondition or setup
        List<Employee> listOfEmployees = List.of(
                Employee.builder().firstName("John").lastName("Doe").email("john.doe@xyz,xyz").build(),
                Employee.builder().firstName("Jane").lastName("Doe").email("jane.doe@xyz,xyz").build());
        //stub method employeeService.getAllEmployees()
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect((status().isOk()))
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

    //positive scenario - valid employee id
    @DisplayName("JUnit test for getEmployeeById() positive scenario")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnObject() throws Exception {

        //given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,xyz")
                .build();
        //stub method employeeService.getEmployeeById()
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //negative scenario - invalid employee id
    @DisplayName("JUnit test for getEmployeeById() negative scenario")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {

        //given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,xyz")
                .build();
        //stub method employeeService.getEmployeeById()
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //positive scenario - valid employee id
    @DisplayName("JUnit test for updateEmployee() positive scenario")
    @Test
    public void givenEmployeeIdAndUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject()
            throws Exception {

        //given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,xyz")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@xyz,xyz")
                .build();
        //stub method employeeService.getEmployeeById()
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
        given(employeeService.updateEmployee(updatedEmployee)).willAnswer(invocation -> invocation.getArgument(0));

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    //negative scenario - invalid employee id
    @DisplayName("JUnit test for updateEmployee() negative scenario")
    @Test
    public void givenEmployeeIdAndUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {

        //given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,xyz")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@xyz,xyz")
                .build();

        //stub method employeeService.getEmployeeById()
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }


}
