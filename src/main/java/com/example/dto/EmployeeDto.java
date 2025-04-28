package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
}
