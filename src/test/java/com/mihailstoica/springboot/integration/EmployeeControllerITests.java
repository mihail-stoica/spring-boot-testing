package com.mihailstoica.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihailstoica.springboot.model.Employee;
import com.mihailstoica.springboot.repository.EmployeeRepository;
import com.mihailstoica.springboot.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {

        employeeRepository.deleteAll();
    }

    @DisplayName("JUnit test for createEmployee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@xyz,xyz")
                .build();

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

        employeeRepository.saveAll(listOfEmployees);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect((status().isOk()))
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

}
