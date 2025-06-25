package com.example.EMP.EMPRepositoryTest.api.controller;

import com.example.EMP.controller.EmployeeController;
import com.example.EMP.dto.EmployeeDto;
import com.example.EMP.entity.Employee;
import com.example.EMP.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService; // correct use for controller test

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDto inputDto;
    private Employee savedEntity;

    @BeforeEach
    void setUp() {
        inputDto = new EmployeeDto();
        inputDto.setFirstName("Sushmitha");
        inputDto.setLastName("S");
        inputDto.setEmail("sush@example.com");

        savedEntity = new Employee();
        savedEntity.setId(1L);
        savedEntity.setFirstName("Sushmitha");
        savedEntity.setLastName("S");
        savedEntity.setEmail("sush@example.com");


    }

    @Test
    public void EmployeeController_CreateEmployee_ReturnCreated()throws Exception{

        when(employeeService.createEmployee(any(EmployeeDto.class)))
                .thenReturn(inputDto);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(inputDto)));

    }

    @Test
    public void EmployeeController_GetEmployeeById_ReturnEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(1L,"John", "Doe", "john.doe@example.com");

        when(employeeService.getEmployeeById(1L)).thenReturn(employeeDto);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void EmployeeController_UpdateEmployee_ReturnUpdated() throws Exception {
        EmployeeDto updatedDto = new EmployeeDto(1L,"Jane", "Smith", "jane.smith@example.com");

        when(employeeService.updateEmployee(eq(1L), any(EmployeeDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/api/employees/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"));
    }


    @Test
    public void EmployeeController_DeleteEmployee_ReturnOk() throws Exception {
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));
    }




}
