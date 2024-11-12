package com.springProject.OrganizationDTO;

import java.time.LocalDate;
import java.util.List;

import com.springProject.Entity.Organization;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeResponseDto {

    private Integer empId;
    private String empFullname;
    private String empUsername;
    private String empPassword;
    private String empContact;
    private boolean empStatus;
    private String empRole;
    private LocalDate empDoj;
    private Organization organization;
//    private List<Project> projects;


}
