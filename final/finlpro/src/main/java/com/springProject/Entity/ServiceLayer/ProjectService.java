package com.springProject.Entity.ServiceLayer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.springProject.Entity.Employee;
import com.springProject.Entity.Project;

public interface ProjectService {
	
	public String AddProject(Project p);
	public String CloseProject(int id);
	public Project getProjectById(int PId);
	public List<Project> fetchAllProjectsByOrg(String orgUsername);
	public int UpdateProject(Project p);
	public String addEmployeeToProject(Set<Integer> s,Integer pid);
	public List<Employee> getEmployeeInProject(Integer pId);
	public String addEmployee(List<Integer> empId, Integer projectId);
	public ResponseEntity<String> removeEmployeeFromProject(Integer projectId,Integer empId);
	public ResponseEntity<String> closeProject(Integer projectId);
	public ResponseEntity<List<Project>> getProjectsByOrgUsernameStatus(String orgUsername, String status);
	ResponseEntity<List<Map<String, String>>> getProjectsByEmpUsername(String empUsername);
}