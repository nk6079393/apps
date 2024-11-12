package com.springProject.Entity.ServiceLayer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.springProject.OrganizationDTO.EmployeeDto;
import com.springProject.OrganizationDTO.EmployeeResponseDto;
import com.springProject.OrganizationDTO.LoginRequest;

public interface EmployeeService {
	
    ResponseEntity<EmployeeResponseDto> addEmployee(EmployeeDto newempDto);

    EmployeeResponseDto updateEmployee(EmployeeDto ud, Integer empid);

    String deleteemp(Integer eid);

    List<EmployeeResponseDto> viewAll();

    List<String> viewAllByName();

    List<String> viewAllByRole(String Role);

    ResponseEntity<String> loginemp(LoginRequest loginRequest);
    
    List<EmployeeResponseDto> viewAllByRole(String roleName,String username);

	ResponseEntity<List<String>> getEmpNamesByProjectId( Integer id);
	
	ResponseEntity<String> getRoleByEmpUsername(String username);
}
