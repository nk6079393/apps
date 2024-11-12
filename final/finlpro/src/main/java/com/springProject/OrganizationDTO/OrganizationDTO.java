package com.springProject.OrganizationDTO;

import java.util.Base64;
import java.util.List;

import com.springProject.Entity.Organization;

import lombok.Data;

@Data
public class OrganizationDTO {

	private Integer orgId;
	private String orgUsername;
	private String orgName;
	private Long orgContact;
	private String orgPassword;
	private byte[] orgImage;
	private String orgImageType;
	
	//private List<Employee> employee;
}
