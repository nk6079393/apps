package com.springProject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springProject.Entity.ServiceLayer.EmployeeServiceImpl;
import com.springProject.OrganizationDTO.EmployeeDto;
import com.springProject.OrganizationDTO.EmployeeResponseDto;
import com.springProject.OrganizationDTO.LoginRequest;
import com.springProject.OrganizationDTO.OrganizationDTO;

@CrossOrigin(origins = {"http://localhost:56999", "http://localhost:4200"}) // Correct format
@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
    EmployeeServiceImpl empser;
	
	@PostMapping("/add")
	public ResponseEntity<EmployeeResponseDto> addemp(@RequestBody EmployeeDto request)
	{
		return  empser.addEmployee(request);
	}
	
	@GetMapping("/view")
	public ResponseEntity<List<EmployeeResponseDto>> view()
	{
		List<EmployeeResponseDto> employeeResponseDto= empser.viewAll();

		return new ResponseEntity<>(employeeResponseDto,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public EmployeeResponseDto updateemp(@RequestBody EmployeeDto request,@PathVariable Integer id)
	{
		return empser.updateEmployee(request, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> addemp(@PathVariable Integer id)
	{
		String response =empser.deleteemp(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/viewByname")
	public ResponseEntity<List<String>> viewByname()
	{
		List<String> employeeName= empser.viewAllByName();
		 return new ResponseEntity<>(employeeName, HttpStatus.OK);
	}

	@GetMapping("/viewByRole/{role}")
	public ResponseEntity<List<String>> viewByRole(@PathVariable String role)
	{
		List<String> employeeName= empser.viewAllByRole(role);
		return new ResponseEntity<>(employeeName, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String > login(@RequestBody LoginRequest loginRequest)
	{
		return  empser.loginemp(loginRequest);
	}
	
	@GetMapping("/get-emps/{username}")
	public ResponseEntity<List<EmployeeResponseDto>> viewAllByorg( @PathVariable String username)
	{
		List<EmployeeResponseDto> employee= empser.viewAllActive(username);
		 return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	@GetMapping("/viewByRole/{role}/{username}")
	public ResponseEntity<List<EmployeeResponseDto>> viewByRole(@PathVariable String role, @PathVariable String username)
	{
		List<EmployeeResponseDto> employees= empser.viewAllByRole(role, username);
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	@GetMapping("/getempById/{eid}")
	public ResponseEntity< EmployeeResponseDto>  viewById( @PathVariable Integer eid)
	{
		EmployeeResponseDto employee= empser.getEmployeeById(eid);
		 return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	@GetMapping("/get-bench-emps/{username}/{id}")
	public ResponseEntity<List<EmployeeResponseDto>> getMethodName(@PathVariable String username,@PathVariable Integer id) {
		return ResponseEntity.ok(empser.getEmployeeNotInProject(username, id));
	}
	
	@GetMapping("/get-by-project/{id}")
	public ResponseEntity<List<String>> getEmpNamesByProjectId(@PathVariable Integer id){
		return empser.getEmpNamesByProjectId(id);
	}
	
	@GetMapping("/get-role-by/{username}")
	public ResponseEntity<String> getRoleByEmpUsername(@PathVariable String username){
		return empser.getRoleByEmpUsername(username);
	}
	

}

