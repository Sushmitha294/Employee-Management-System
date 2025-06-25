package com.example.EMP.service;

import com.example.EMP.EmployeeMapper.EmployeeMapper;
import com.example.EMP.dto.EmployeeDto;
import com.example.EMP.entity.Employee;
import com.example.EMP.exception.ResourceNotFoundException;
import com.example.EMP.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        // Convert EmployeeDto to Employee entity
        Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
        // Save the employee entity to the database
        Employee savedEmployee = employeeRepository.save(employee);
        // Convert the saved Employee entity back to EmployeeDto
        EmployeeDto savedEmployeeDto = EmployeeMapper.mapToEmployeeDto(savedEmployee);
        // Return the saved EmployeeDto
        if (savedEmployeeDto != null) {
            return savedEmployeeDto;
        }
        // If the savedEmployeeDto is null, return null or handle the error as needed
        return null;
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {

    Employee employee =    employeeRepository.findById(employeeId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Employee is not Exist with given id:"+employeeId));


        return EmployeeMapper.mapToEmployeeDto(employee);


    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

       List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee)->
                EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new ResourceNotFoundException("Employee is not Exist with given id:" + employeeId));
        // Convert EmployeeDto to Employee entity
        // Set the ID of the employee to update
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        // Save the updated employee entity to the database
        Employee updatedEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);

    }

    @Override
    public void deleteEmployee(Long employeeId) {

        Employee employee =    employeeRepository.findById(employeeId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Employee is not Exist with given id:"+employeeId));

        employeeRepository.deleteById(employeeId);

    }


    @Override
    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(EmployeeMapper::mapToEmployeeDto);
    }




}
