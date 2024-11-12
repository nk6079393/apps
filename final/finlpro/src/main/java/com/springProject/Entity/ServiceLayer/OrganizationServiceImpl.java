package com.springProject.Entity.ServiceLayer;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springProject.Entity.Employee;
import com.springProject.Entity.Organization;
import com.springProject.Entity.Project;
import com.springProject.Entity.Repository.OrganizationRepository;
import com.springProject.OrganizationDTO.EmployeeResponseDto;
import com.springProject.OrganizationDTO.LoginDTO;
import com.springProject.OrganizationDTO.OrganizationDTO;

@Service
public class OrganizationServiceImpl implements OrganizationService{

	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ResponseEntity<String> insertOrganizaitonDetails(OrganizationDTO organizationDTO, MultipartFile orgImage) {
		
		List<String> names=organizationRepository.getAllNames();
		if(names.contains(organizationDTO.getOrgName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("organization name already exists");
		}
		
		List<String> usernames=organizationRepository.getAllUsernames();
		if(usernames.contains(organizationDTO.getOrgUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("organization username already exists");
		}

	    try {
	    	organizationDTO.setOrgImageType(orgImage.getContentType());
			organizationDTO.setOrgImage(orgImage.getBytes());
		} catch (IOException e) {			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error in image");
		}
	    // Convert DTO to Entity and save
	    Organization organization = modelMapper.map(organizationDTO, Organization.class);
	    Organization savedOrganization = organizationRepository.save(organization);

	    // Convert saved entity back to DTO for the response
	    OrganizationDTO savedOrganizationDTO = modelMapper.map(savedOrganization, OrganizationDTO.class);
	    if(savedOrganizationDTO!=null)
	    	return ResponseEntity.status(HttpStatus.CREATED).body("data saved");
	    else
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to save organization details");

	}


	@Override
	public List<OrganizationDTO> getAllOrganizationDetails() {
		
		return organizationRepository.findAll().stream()
				.map(org -> modelMapper.map(org , OrganizationDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<LoginDTO> login(LoginDTO longDTO) {
		
		Organization organization = organizationRepository.findByUserNameAndOrgPasswordUsingQuery(longDTO.getOrgUsername(), longDTO.getOrgPassword());
		
		if (organization != null) {
	    	LoginDTO orgDetails = modelMapper.map(organization , LoginDTO.class);
	    	orgDetails.setOrgPassword(null);
	        return ResponseEntity.ok(orgDetails);
	    } else {
	       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	    }
	}

	@Override
	public OrganizationDTO forGotpassword(OrganizationDTO organizationDTO , String newPassword) {
		Organization organization = organizationRepository
			.forgotPassword(organizationDTO.getOrgUsername() , organizationDTO.getOrgContact());
		
		if(organization == null) {
		
			throw new RuntimeException("user not found or contact number does not match");
		}
			organization.setOrgPassword(newPassword);
			organizationRepository.save(organization);
			return modelMapper.map(organization, OrganizationDTO.class);
		}

	@Override
    public ResponseEntity<Map<String, Object>> getOrgByUsername(String userName) {
        Organization organization = organizationRepository.findByUsername(userName);
        
        if (organization == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orgName", organization.getOrgName());
        response.put("orgUsername", organization.getOrgUsername());
        response.put("orgImageType",organization.getOrgImageType());
        String base64Image = Base64.getEncoder().encodeToString(organization.getOrgImage());
        response.put("orgImage", base64Image);  // Send Base64 encoded image
        return ResponseEntity.ok(response);
    }
	
	@Override
	public ResponseEntity<OrganizationDTO> getIdByUsername(String username) {
		Organization organization = organizationRepository.findByUsername(username);
		
		if(organization !=null) {
			OrganizationDTO organizationDTO = modelMapper.map(organization, OrganizationDTO.class);
			organizationDTO.setOrgPassword(null);
			organizationDTO.setOrgImage(null);
			organizationDTO.setOrgImageType(null);
			organizationDTO.setOrgContact(null);
			return ResponseEntity.ok(organizationDTO);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); 
	}
	
	@Override
	public Organization getOrganizationByOrgUsername(String username) {
		// TODO Auto-generated method stub
		return organizationRepository.findByOrgUsername(username);
	}
	
	
	public List<Employee> getEmployeesByOrganization(Integer Id)
	{
		return organizationRepository.findById(Id).get().getEmployees();
	}


	@Override
	public ResponseEntity<Map<String, Integer>> orgDashboardData(String orgUsername) {
		Map<String,Integer> dashboardData=new HashMap<>();
		
		Organization organization = organizationRepository.findByUsername(orgUsername);
		List<Employee> employees = organization.getEmployees().stream().filter(Employee::isEmpStatus).toList();
		List<Project> projects = organization.getProjects();
		int totalEmployees=employees.size();
		int totalProjects=projects.size();
		if(totalEmployees==0 && totalProjects==0) {
			return ResponseEntity.noContent().build();
		}
		if(totalEmployees!=0) {
			dashboardData.put("totalOrgEmployees", totalEmployees);
			int totalDevelopers=(int)employees.stream()
					.filter(emp->emp.isEmpStatus()&&emp.getEmpRole().equalsIgnoreCase("developer"))
					.count();
			dashboardData.put("developers", totalDevelopers);
			int totalTesters=(int)employees.stream()
					.filter(emp->emp.isEmpStatus()&&emp.getEmpRole().equalsIgnoreCase("tester"))
					.count();
			dashboardData.put("testers", totalTesters);
		}
		if(totalProjects!=0) {
			dashboardData.put("totalProjects", totalProjects);
			int pendingProjects=(int)projects.stream()
					.filter(project->project.getProjectStatus().equalsIgnoreCase("active"))
					.count();
			dashboardData.put("pendingProjects", pendingProjects);
			dashboardData.put("completedProjects", totalProjects-pendingProjects);
		}
		return ResponseEntity.ok(dashboardData);
	}

	@Override
	public ResponseEntity<String> getOrgNameByEmpUsername(String empUsername) {
		String orgName=organizationRepository.getOrgNameByEmpUsername(empUsername) ;
		if(orgName==null)
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(orgName);
	}
	
}
