package com.example.learning.EmployeeManagement.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

  private Long id;

  private String name;

  private String email;

  private String age;

  private LocalDate dateOfJoining;

  private Boolean iActive;

  private String role;

  private Double salary;

}
