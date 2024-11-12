package com.springProject.Entity.ServiceLayer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springProject.Entity.Employee;
import com.springProject.Entity.Organization;
import com.springProject.Entity.Project;
import com.springProject.Entity.Repository.EmployeeRepository;
import com.springProject.Entity.Repository.OrganizationRepository;
import com.springProject.Entity.Repository.ProjectRepo;


@Service
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	private ProjectRepo projectRepo;
	
	@Autowired
	private OrganizationRepository orgRepo;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public String AddProject(Project p) {

		System.out.println(p.getOrganization().getOrgId()+p.getProjectName());
        Project isAvailProject = projectRepo.findByOrgIdAndProjectName(p.getOrganization().getOrgId(),p.getProjectName());
        
        System.out.println(isAvailProject);
        
        if(isAvailProject==null) {
        	 Integer id = projectRepo.save(p).getProjectId ();
        	 return "Project saved with ID: " + id;
        }
   
        return "error";
        // Save the project and retrieve the ID
	}

	@Override
	public String CloseProject(int id) {
		// TODO Auto-generated method stub
		Optional<Project> project=projectRepo.findById(id);
		if(project.isEmpty()) {
			return "project with"+id+"not found";
		}
		else {
		Project pr=	project.get();
		pr.setProjectEndDate(LocalDate.now());
		pr.setProjectStatus("closed");
		projectRepo.save(pr);
		return pr.getProjectName()+"with"+id+"was closed";

	}}

	@Override
	public Project getProjectById(int PId) {
		// TODO Auto-generated method stub
		return projectRepo.findById(PId).get();
	}
	
	 @Override
	 public int UpdateProject(Project project) {
	     
		 int row=projectRepo.updateProject(project.getProjectEndDate(),
				 project.getProjectDescription(), project.getProjectId());
		 
        if (row>0) {
            return row;
        }

	    return 0;
	    
	}

	@Override
	public String addEmployeeToProject(Set<Integer> s, Integer pid) {
		
		return null;
	}

	@Override
	public List<Project> fetchAllProjectsByOrg(String orgUsername) {
		
		Organization orgByUsername = orgRepo.findByOrgUsername(orgUsername);
				
		return projectRepo.findByOrgId(orgByUsername.getOrgId());
	}
	
	@Override
	public List<Employee>  getEmployeeInProject(Integer pId)
	{
		 Project project = projectRepo.findById(pId).get();
		 
		  return project.getEmployees();
	}
	
	@Override
    public String addEmployee(List<Integer> empId, Integer projectId) {
        Project project = getProjectById(projectId);

        List<Employee> employees = employeeRepository.findAllById(empId);
        project.getEmployees().addAll(employees);
        try {
        	projectRepo.save(project);
        	return "emp added";
        }catch(Exception e) {
        	return null;
        }
    }

	@Override
	public ResponseEntity<String> removeEmployeeFromProject(Integer projectId, Integer empId) {

		int removedEmp = projectRepo.removeEmpFromProject(projectId, empId);
	    if(removedEmp>0) {
	        return ResponseEntity.ok("deleted");
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
		
	}

	@Override
	public ResponseEntity<String> closeProject(Integer projectId) {
		
		int closeProject=projectRepo.closeProject(projectId);
		if(closeProject>0) {
			return ResponseEntity.ok("closed");
		}
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<List<Project>> getProjectsByOrgUsernameStatus(String orgUsername, String status) {
		List<Project> filterProjects=projectRepo.getProjectsByOrgUsernameStatus( orgUsername,  status);
		if(filterProjects.size()==0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(filterProjects);
	}

	@Override
	public ResponseEntity<List<Map<String, String>>> getProjectsByEmpUsername(String empUsername) {
		
		
		Employee employee = employeeRepository.findByEmpUsername(empUsername).get();
		if(employee!=null) {
			int empId=employee.getEmpId();
			List<Map<String, String>> listProjects=new ArrayList<>();
			List<Integer> projectIds = projectRepo.getProjectIdsByEmpId(empId);
			List<String> projectsNames=projectRepo.getProjectNamesByEmpId(empId);
			
			for(int i=0;i<projectIds.size();i++) {
				Map<String, String> map=new HashMap<>();
				map.put("id", projectIds.get(i)+"");
				map.put("name", projectsNames.get(i));
				listProjects.add(map);
			}
			return ResponseEntity.ok(listProjects);
		}
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public ResponseEntity<List<Project>> findByOrgIdAndProjectNamecontainsname(String orgUsername, String projectName) {
		List<Project> filterProjects=projectRepo.findByOrgIdAndProjectNamecontain( orgUsername,  projectName);
		if(filterProjects.size()==0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(filterProjects);
	}
}
