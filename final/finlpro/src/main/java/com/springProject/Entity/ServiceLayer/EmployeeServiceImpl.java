package com.springProject.Entity.ServiceLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springProject.Entity.Employee;
import com.springProject.Entity.Organization;
import com.springProject.Entity.Repository.EmployeeRepository;
import com.springProject.OrganizationDTO.EmployeeDto;
import com.springProject.OrganizationDTO.EmployeeResponseDto;
import com.springProject.OrganizationDTO.LoginRequest;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository emprepo;

	@Autowired
	private ModelMapper model;
	
	@Autowired
	private OrganizationServiceImpl orgser;
	
	@Autowired
	private ProjectService projectService;

	@Override
	public ResponseEntity<EmployeeResponseDto> addEmployee(EmployeeDto newempDto) {

			//Role findByRolename = rolerepo.findByRolename(newempDto.getRole());
			Employee employeeMapModel = model.map(newempDto, Employee.class);
			employeeMapModel.setEmpStatus(true);			
			
			
			Employee isAvailWithCont=emprepo.findByEmpContact(employeeMapModel.getEmpContact());
			
			if(isAvailWithCont==null) {
			
				Employee isAvailEmp = emprepo.findByEmpUsernameAndOrgId(employeeMapModel.getEmpUsername(),employeeMapModel.getOrganization().getOrgId());
				
				if(isAvailEmp==null) {
					Employee employee = emprepo.save(employeeMapModel);
					return ResponseEntity.status(HttpStatus.CREATED).body(model.map(employee, EmployeeResponseDto.class));
				}else{
					return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
				}
			}
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
			
	}

	@Override
	public EmployeeResponseDto updateEmployee(EmployeeDto ud, Integer empid) {


		Optional<Employee> byId = emprepo.findById(empid);
		if (byId.isPresent()) {
			Employee emp = byId.get();
			emp.setEmpFullname(ud.getEmpFullname());
			emp.setEmpPassword(ud.getEmpPassword());
			emp.setEmpContact(ud.getEmpContact());
			emp.setEmpRole(ud.getEmpRole());
			emp.setEmpUsername(ud.getEmpUsername());

			Employee res = emprepo.save(emp);

			return model.map(res, EmployeeResponseDto.class);

		} else {
			return null;
		}


	}
	@Override
	public String deleteemp(Integer  eid) {

			Optional<Employee> byId = emprepo.findById(eid);
			if (byId.isPresent()) {
				Employee emp = byId.get();
				emp.setEmpStatus(false);
				emprepo.save(emp);
				return "deleted";
			} else
				return "";
	}

	@Override
	public  List<EmployeeResponseDto> viewAll() {

		List<Employee> employeeList= emprepo.findAll().stream().filter(Employee::isEmpStatus).toList();
		return  employeeList.stream().map(employee ->model.map(employee, EmployeeResponseDto.class)).toList();
	}

	@Override
	public List<String> viewAllByName() {

		List<Employee> all = emprepo.findAll().stream().filter(Employee::isEmpStatus).toList();
		return all.stream().map(Employee::getEmpFullname).toList();
	}

	@Override
	public List<String> viewAllByRole(String roleName) {

		List<Employee> all = emprepo.findAll().stream().filter(employee -> employee.isEmpStatus() && employee.getEmpRole().equals(roleName)).toList();
		return all.stream().map(Employee::getEmpFullname).toList();
	}

	@Override
	public ResponseEntity<String> loginemp(LoginRequest loginRequest) {
		
		String empRole= emprepo.findByUsernameAndPassword(loginRequest.getEmpUsername(), loginRequest.getEmpPassword());
		if(empRole==null) {
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(null);
		}
		return  ResponseEntity.status(HttpStatus.ACCEPTED).body(empRole);
	}

	public List<EmployeeResponseDto> viewAllActive(String username)
	{
		Organization organizationByorgUsername = orgser.getOrganizationByOrgUsername(username);
		
		List<Employee> employees =organizationByorgUsername.getEmployees().stream().filter(Employee::isEmpStatus).toList();
	
		return employees.stream().map(employee ->model.map(employee, EmployeeResponseDto.class)).toList();
	}

	 public  EmployeeResponseDto  getEmployeeById(Integer eId)
	 {	 
		 Employee emp=emprepo.findById(eId).orElseThrow(()-> new EntityNotFoundException());
		 return model.map(emp, EmployeeResponseDto.class);
	 }

	@Override
	public List<EmployeeResponseDto> viewAllByRole(String roleName,String username) {
		List<Employee> employe=orgser.getOrganizationByOrgUsername(username).getEmployees();
	
		List<Employee> all = employe.stream().filter(employee -> employee.isEmpStatus() && employee.getEmpRole().equals(roleName)).toList();
		return all.stream().map(employee ->model.map(employee, EmployeeResponseDto.class)).toList();
	}
	
	
	public List<EmployeeResponseDto> getEmployeeNotInProject(String orgusername , Integer projectId)
	{
		  
		 Organization org= orgser.getOrganizationByOrgUsername(orgusername);
		 List<Employee>  employees=orgser.getEmployeesByOrganization(org.getOrgId());
	     List<Employee> allEmployeeInProject =  projectService.getEmployeeInProject(projectId);
	     employees.removeAll(allEmployeeInProject);
	     
      return  employees.stream()
    		  .map(emp ->model.map(emp, EmployeeResponseDto.class))
    		  .map(emp->{
    			  emp.setOrganization(null);
    			  return emp;})
    		  .toList();
	}

	@Override
	public ResponseEntity<List<String>> getEmpNamesByProjectId(Integer id) {
		List<String> empNames=emprepo.getEmpNamesByProjectId(id);
		if(empNames.size()>0) {
			return ResponseEntity.ok(empNames);
		}
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<String> getRoleByEmpUsername(String username) {
		
		return ResponseEntity.ok(emprepo.getRoleByEmpUsername(username));
		
	}
}
