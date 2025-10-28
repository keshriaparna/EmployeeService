package com.example.learning.EmployeeManagement.services;

import com.example.learning.EmployeeManagement.dto.EmployeeDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface EmployeeService {

  public Optional<EmployeeDTO> getEmployeeById(Long id);

  public List<EmployeeDTO> getAllEmployee();

  public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee);

  public EmployeeDTO updateEmployeeById(Long id,EmployeeDTO employeeDTO);

  public void deleteEmployeeById(Long id);

  public EmployeeDTO updatePartialEmployeeById(Long id, Map<String,Object> updates);

}
