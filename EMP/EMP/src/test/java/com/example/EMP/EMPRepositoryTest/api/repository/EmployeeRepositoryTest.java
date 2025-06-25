package com.example.EMP.EMPRepositoryTest.api.repository;
import com.example.EMP.entity.Employee;
import com.example.EMP.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Save employee and find by ID")
    public void testSaveAndFindById() {
        // Arrange
        Employee employee = new Employee();
        employee.setFirstName("Sushmitha");
        employee.setLastName("S");
        employee.setEmail("sush@example.com");

        // Act
        Employee saved = employeeRepository.save(employee);
        Optional<Employee> found = employeeRepository.findById(saved.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Sushmitha");
    }
    @Test
    @DisplayName("Find all employees")
    public void testFindAllEmployees() {
        // Arrange
        Employee emp1 = new Employee(null, "Sushmitha", "S", "sush1@example.com");
        Employee emp2 = new Employee(null, "Akhila", "A", "akhila@example.com");

        employeeRepository.save(emp1);
        employeeRepository.save(emp2);

        // Act
        List<Employee> employees = employeeRepository.findAll();

        // Assert
        assertThat(employees).isNotEmpty();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Delete employee by ID")
    public void testDeleteById() {
        // Arrange
        Employee employee = new Employee();
        employee.setFirstName("Sush");
        employee.setLastName("M");
        employee.setEmail("delete@example.com");

        Employee saved = employeeRepository.save(employee);
        Long id = saved.getId();

        // Act
        employeeRepository.deleteById(id);

        // Assert
        Optional<Employee> deleted = employeeRepository.findById(id);
        assertThat(deleted).isEmpty();
    }
}
