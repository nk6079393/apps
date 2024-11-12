package com.springProject.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name="projects")
@Data
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer projectId;

	private String  projectName;
	private String projectStatus;
	private String projectDescription;
	private LocalDate projectStartDate;
	private LocalDate projectEndDate;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
	       name = "project_employee", // Join table name
	       joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "projectId"), // Foreign key for Project
	       inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "empId") // Foreign key for Employee
	 )
	 private List<Employee> employees=new ArrayList<>();
	
//	@ManyToOne(targetEntity=Organization.class, cascade = CascadeType.MERGE)
//	@JoinColumn(name = "org_id")
//	private Organization organization;
	public Project() {
		projectStatus="active";
		projectStartDate=LocalDate.now();
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="org_Id")
	private Organization organization;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Tickets> tickets = new ArrayList<>();
}
