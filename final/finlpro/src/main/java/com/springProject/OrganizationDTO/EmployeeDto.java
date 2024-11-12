package com.springProject.OrganizationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.springProject.Entity.Organization;

import lombok.Data;

@Data
public class EmployeeDto {
	private String empFullname;
	private String empUsername;
	private String empPassword;
	private Long empContact;// Assuming you want to show the role name, otherwise you can include the full RoleDto
	private LocalDate empDoj;
	private String empRole;
	private Organization organization;

}
