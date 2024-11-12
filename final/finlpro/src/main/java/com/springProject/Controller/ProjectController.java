package com.springProject.Controller;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.springProject.Entity.Project;
import com.springProject.Entity.ServiceLayer.ProjectServiceImpl;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = {"http://localhost:56999", "http://localhost:4200"}) // Correct format
@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
    private  ProjectServiceImpl projectService;
    
    @PutMapping("/update-project")
    public ResponseEntity<Integer> updateProject(@RequestBody Project project) {
    	return ResponseEntity.ok(projectService.UpdateProject(project));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        String response = projectService.AddProject(project);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<String> closeProject(@PathVariable int id) {
        String response = projectService.CloseProject(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-project/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable int id) {
       Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/get-projects/{orgUsername}")
    public ResponseEntity<List<Project>> fetchAllProjects(@PathVariable String orgUsername) {
    	List<Project> projects = projectService.fetchAllProjectsByOrg(orgUsername);
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/add-emp-to-project/{projectId}")
    public ResponseEntity<String> viewEmployeeOnProject(@PathVariable Integer projectId,@RequestBody List<Integer> employeeIds)
    {
    	return ResponseEntity.ok(projectService.addEmployee(employeeIds,projectId));
    }
    
    @DeleteMapping("/remove-emp-project/{projectId}/{empId}")
    public ResponseEntity<String> removeEmployeeFromProject(@PathVariable Integer projectId,@PathVariable Integer empId ){
    	return projectService.removeEmployeeFromProject(projectId,empId);
    }
    
    @DeleteMapping("/close-project/{projectId}")
    public ResponseEntity<String> closeProject(@PathVariable Integer projectId){
    	return projectService.closeProject(projectId);
    }
    
    @GetMapping("/get-projects-by/{orgUsername}/{status}")
    public ResponseEntity<List<Project>> getProjectsByOrgUsernameStatus(@PathVariable String orgUsername,@PathVariable String status) {
        return projectService.getProjectsByOrgUsernameStatus(orgUsername,status); 
    }
    
    @GetMapping("/get-projects-by-empUsername/{empUsername}")
    public ResponseEntity<List<Map<String, String>>> getProjectsByEmpUsername(@PathVariable String empUsername){

    	return projectService.getProjectsByEmpUsername(empUsername);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Project>> findByOrgIdAndProjectNamehaving(
            @RequestParam String orgUsername, 
            @RequestParam String projectName) {
        
        List<Project> filterProjects = projectRepo.findByOrgIdAndProjectNameContaining(orgUsername, projectName);
        
        if (filterProjects.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filterProjects);
    }
    @GetMapping("/get-projects-by-projectName/{orgUsername}/{status}")
    public ResponseEntity<List<Project>> getProjectsByOrgUsernameProjName(@PathVariable String orgUsername,@PathVariable String status) {
        return projectService.findByOrgIdAndProjectNamecontainsname(orgUsername, status); 
    }
    
}

