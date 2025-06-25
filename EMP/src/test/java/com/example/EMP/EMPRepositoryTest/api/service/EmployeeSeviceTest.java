package com.example.EMP.EMPRepositoryTest.api.service;

import com.example.EMP.dto.EmployeeDto;
import com.example.EMP.entity.Employee;
import com.example.EMP.repository.EmployeeRepository;
import com.example.EMP.service.EmployeeService;
import com.example.EMP.service.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeSeviceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


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
    public  void EmployeeService_CreateEmployee_ReturnsEmployeeDto(){



        EmployeeDto employeeDto = EmployeeDto.builder().firstName("Sushmitha").lastName("s").email("sushm@gmail.com").build();
        // Mock the save method of the repository
        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(savedEntity);

        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);

        Assertions.assertThat(savedEmployee).isNotNull();

    }

    @Test
    public void EmployeeService_GetAllEmployee_ReturnsEmployeeDto(){

        // Arrange
        List<Employee> employees = List.of(savedEntity);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

        // Mock repository
        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);

        // Act
        Page<EmployeeDto> result = employeeService.getAllEmployees(pageable);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getContent()).hasSize(1);


    }

    @Test
    public void getEmployeeById_whenIdExists_shouldReturnEmployeeDto() {
        // Arrange
        Long employeeId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(savedEntity));

        // Act
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);

        // Assert
        Assertions.assertThat(employeeDto).isNotNull();
        Assertions.assertThat(employeeDto.getFirstName()).isEqualTo("Sushmitha");
    }

    @Test
    public void updateEmployee_whenValidId_shouldReturnUpdatedEmployeeDto() {
        // Arrange
        Long id = 1L;
        EmployeeDto updatedDto = new EmployeeDto();
        updatedDto.setFirstName("Updated");
        updatedDto.setLastName("User");
        updatedDto.setEmail("updated@example.com");

        Employee updatedEntity = new Employee();
        updatedEntity.setId(id);
        updatedEntity.setFirstName("Updated");
        updatedEntity.setLastName("User");
        updatedEntity.setEmail("updated@example.com");

        when(employeeRepository.findById(id)).thenReturn(Optional.of(savedEntity));
        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(updatedEntity);

        // Act
        EmployeeDto result = employeeService.updateEmployee(id, updatedDto);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFirstName()).isEqualTo("Updated");
        Assertions.assertThat(result.getEmail()).isEqualTo("updated@example.com");
    }
    @Test
    public void deleteEmployee_whenValidId_shouldNotThrowException() {
        // Arrange
        Long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.of(savedEntity));

        // Act & Assert
        Assertions.assertThatCode(() -> employeeService.deleteEmployee(id))
                .doesNotThrowAnyException();
    }







}
