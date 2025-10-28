package com.example.learning.EmployeeManagement.controllers;

import com.example.learning.EmployeeManagement.dto.EmployeeDTO;
import com.example.learning.EmployeeManagement.exceptions.ResourceNotFoundException;
import com.example.learning.EmployeeManagement.services.EmployeeService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  @Autowired
  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId){
    Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeId);
    return employeeDTO
        .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
        .orElseThrow(() ->new ResourceNotFoundException("Employee not found with id: "+employeeId));
  }

  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required= false,name = "inputAge") Integer age,
      @RequestParam(required = false) String sortBy){
    return ResponseEntity.ok(employeeService.getAllEmployee());
  }

  @PostMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
    EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
    return new ResponseEntity<>(savedEmployee,HttpStatus.CREATED);
  }

  @PutMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO inputEmployee,@PathVariable Long employeeId){
    return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, inputEmployee));
  }

  @DeleteMapping(path = "/{employeeId}")
  public void deleteEmployeeById(@PathVariable Long employeeId){
    employeeService.deleteEmployeeById(employeeId);
  }

  @PatchMapping
  public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates,
                                    @PathVariable Long employeeId) {
    EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId, updates);
    if(employeeDTO == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(employeeDTO);
  }

}
