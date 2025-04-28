package com.example.ui.rest;

import com.example.dto.EmployeeDto;
import com.example.services.EmployeeServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "API endpoints for task CRUD operations")
public class EmployeeController {
    private final EmployeeServices employeeServices;

    @Autowired
    public EmployeeController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeServices.getAllEmployees();
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeServices.createEmployee(employeeDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return employeeServices.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Kitap güncelle", description = "Belirtilen ID'ye sahip employee günceller")
    @ApiResponse(responseCode = "200", description = "employee başarıyla güncellendi")
    @ApiResponse(responseCode = "404", description = "Güncellenecek employee bulunamadı")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody  EmployeeDto employeeDto){
        return employeeServices.updateEmployee(id,employeeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable  Long id){
        return employeeServices.deleteEmployee(id);
    }

}
