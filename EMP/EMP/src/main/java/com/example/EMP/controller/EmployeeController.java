package com.example.EMP.controller;

import com.example.EMP.dto.EmployeeDto;
import com.example.EMP.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeId(@PathVariable("id") Long employeeId) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5")int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDto> employeePage = employeeService.getAllEmployees(pageable);
        return new ResponseEntity<>(employeePage.getContent(), HttpStatus.OK);

    }

    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id")  Long employeeId,
                                            @RequestBody EmployeeDto updatedEmployee )
    {
       EmployeeDto employeeDto= employeeService.updateEmployee(employeeId, updatedEmployee);
        return  ResponseEntity.ok(employeeDto);


    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId)
    {
        employeeService.deleteEmployee(employeeId);
        return  ResponseEntity.ok("Employee deleted successfully");
    }




}
