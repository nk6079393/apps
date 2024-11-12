package com.springProject.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springProject.Entity.ServiceLayer.OrganizationServiceImpl;
import com.springProject.OrganizationDTO.LoginDTO;
import com.springProject.OrganizationDTO.OrganizationDTO;

@CrossOrigin(origins = {"http://localhost:56999", "http://localhost:4200"}) // Correct format
@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

	@Autowired
	private OrganizationServiceImpl organizationServiceImpl;
	
	@PostMapping(value = "/add")
	public ResponseEntity<String> postData(@RequestPart("organization") OrganizationDTO organizationDTO, 
	                                                @RequestPart("orgImage") MultipartFile orgImage) {
	    return organizationServiceImpl.insertOrganizaitonDetails(organizationDTO, orgImage);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO loginDTO) {
	        return organizationServiceImpl.login(loginDTO);
	}
	
	@PatchMapping("/reset")
	public ResponseEntity<String> resetPassword(@RequestBody OrganizationDTO organizationDTO ,@RequestParam String newPassword) {
		
		try {
			OrganizationDTO resetPasswordorganizationDTO = organizationServiceImpl.forGotpassword(organizationDTO,  newPassword);
			return ResponseEntity.ok("password successfully updated: " + resetPasswordorganizationDTO.getOrgName());
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@GetMapping
	public List<OrganizationDTO> getAllData(){
		
		return organizationServiceImpl.getAllOrganizationDetails();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deteleData(@PathVariable Long id) {
		
//		organizationServiceImpl.delete(id);
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body("error");
	}
	
	@GetMapping("/get-org/{username}")
    public ResponseEntity<Map<String, Object>> getOrgDetails(@PathVariable String username) {
        return organizationServiceImpl.getOrgByUsername(username);
    }
	
	@GetMapping("/get-org-details/{username}")
	public ResponseEntity<OrganizationDTO> getOrgIdByUsername(@PathVariable String username) {
		return organizationServiceImpl.getIdByUsername(username);
	}
	
	@GetMapping("/org-dashboard-data/{orgUsername}")
	public ResponseEntity<Map<String,Integer>> orgDashboardData(@PathVariable String orgUsername) {
		return organizationServiceImpl.orgDashboardData(orgUsername);
	}
	
	@GetMapping("/get-org-name-by/{empUsername}")
	public ResponseEntity<String> getOrgNameByEmpUsername(@PathVariable String empUsername) {
		return organizationServiceImpl.getOrgNameByEmpUsername(empUsername);
	}

}