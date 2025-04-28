package com.example.services.impl;

import com.example.dto.EmployeeDto;
import com.example.entity.EmployeeEntity;
import com.example.repository.EmployeeRepository;
import com.example.services.EmployeeServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeServices {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    //@GetMapping("/employees")
    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (EmployeeEntity employee : employees) {
            employeeDtos.add(entityToDto(employee)); // Entity -> DTO'ya çeviriyoruz
        }
        return employeeDtos;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity=dtoToEntity(employeeDto);
        EmployeeEntity savedEmployee= employeeRepository.save(employeeEntity);
        return entityToDto(savedEmployee);
    }

    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id) {
        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            EmployeeEntity employeeEntity = optionalEmployee.get();
            EmployeeDto employeeDto = entityToDto(employeeEntity);
            return ResponseEntity.ok(employeeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<EmployeeDto> updateEmployee(Long id, EmployeeDto employeeDto) {
        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            EmployeeEntity employeeEntity = optionalEmployee.get();

            // Güncelleme işlemleri
            employeeEntity.setFirstName(employeeDto.getFirstName());
            employeeEntity.setLastName(employeeDto.getLastName());
            employeeEntity.setEmailId(employeeDto.getEmailId());

            // Değiştirilen entity'i kaydet
            EmployeeEntity updatedEmployee = employeeRepository.save(employeeEntity);

            // DTO'ya çevirip dön
            EmployeeDto updatedDto = entityToDto(updatedEmployee);
            return ResponseEntity.ok(updatedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id) {
        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            EmployeeEntity employeeEntity = optionalEmployee.get();
            employeeRepository.delete(employeeEntity);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    // //////////////////////Model mapper/////////////
    @Override
    public EmployeeDto entityToDto(EmployeeEntity employeeEntity) {
        EmployeeDto employeeDto=modelMapper.map(employeeEntity,EmployeeDto.class);
        return employeeDto;
    }

    @Override
    public EmployeeEntity dtoToEntity(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity=modelMapper.map(employeeDto,EmployeeEntity.class);
        return employeeEntity;
    }
}
