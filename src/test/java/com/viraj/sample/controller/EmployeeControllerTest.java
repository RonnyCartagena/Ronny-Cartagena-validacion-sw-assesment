package com.viraj.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viraj.sample.entity.Employee;
import com.viraj.sample.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEmployeesIntegrationTest() throws Exception {
        Employee emp = new Employee("Camila", "Developer");
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp));

        mockMvc.perform(get("/employee/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeName").value("Camila"));
    }

    @Test
    void saveEmployeeIntegrationTest() throws Exception {
        Employee emp = new Employee("Ronny", "Pentester");
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(emp);

        mockMvc.perform(post("/employee/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeName").value("Ronny"));
    }
}