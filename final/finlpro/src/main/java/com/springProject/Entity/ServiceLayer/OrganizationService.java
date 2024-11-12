package com.springProject.Entity.ServiceLayer;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.springProject.Entity.Organization;
import com.springProject.OrganizationDTO.LoginDTO;
import com.springProject.OrganizationDTO.OrganizationDTO;

public interface OrganizationService {

	//using
	public ResponseEntity<Map<String, Object>> getOrgByUsername(String userName);
	//using
	public ResponseEntity<String> insertOrganizaitonDetails(OrganizationDTO organizationDTO , MultipartFile orgImage);
	
	public List<OrganizationDTO> getAllOrganizationDetails();
	
//	public ResponseEntity<String> delete(Long id);
//	
	//using
	public ResponseEntity<LoginDTO> login(LoginDTO loginDTO); 
	
	public OrganizationDTO forGotpassword(OrganizationDTO organizationDTO , String newPassword);
	
	//using
	public ResponseEntity<OrganizationDTO> getIdByUsername(String username);
	
	//using
	public Organization getOrganizationByOrgUsername(String username);
	
	//using
	public ResponseEntity<Map<String,Integer>> orgDashboardData(String orgUsername);
	
	//using
	public ResponseEntity<String> getOrgNameByEmpUsername(String empUsername);
	
}
