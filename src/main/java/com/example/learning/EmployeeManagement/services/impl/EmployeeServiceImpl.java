package com.example.learning.EmployeeManagement.services.impl;

import com.example.learning.EmployeeManagement.dto.EmployeeDTO;
import com.example.learning.EmployeeManagement.entities.EmployeeEntity;
import com.example.learning.EmployeeManagement.exceptions.ResourceNotFoundException;
import com.example.learning.EmployeeManagement.repositories.EmployeeRepository;
import com.example.learning.EmployeeManagement.services.EmployeeService;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private final EmployeeRepository employeeRepository;

  @Autowired
  private final ModelMapper modelMapper;

  @Override
  public Optional<EmployeeDTO> getEmployeeById(Long id) {
     return employeeRepository.findById(id)
         .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));
  }

  @Override
  public List<EmployeeDTO> getAllEmployee() {
    return employeeRepository.findAll()
        .stream()
        .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
    EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
    EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity);
    return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
  }

  private void isExistByEmployeeId(Long employeeId) {
    boolean exists = employeeRepository.existsById(employeeId);
    if(!exists) throw new ResourceNotFoundException("Employee not found with id: "+ employeeId);
  }

  @Override
  public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
    isExistByEmployeeId(employeeId);
    EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
    employeeEntity.setId(employeeId);
    EmployeeEntity updateEmployee = employeeRepository.save(employeeEntity);
    return modelMapper.map(updateEmployee, EmployeeDTO.class);
  }

  @Override
  public void deleteEmployeeById(Long employeeId) {
    isExistByEmployeeId(employeeId);
    employeeRepository.deleteById(employeeId);
  }

  @Override
  public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
    isExistByEmployeeId(employeeId);
    EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
    updates.forEach((field, value)-> {
      Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class,field);
      fieldToBeUpdated.setAccessible(true);
      ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
    });
    return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
  }
}
