package com.springProject.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name="employees")
public class Employee {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer empId;
	private String empFullname;

	private String empUsername;
	private String empPassword;
	
	@Column(unique=true)
	private Long empContact;
	private boolean empStatus;
	private String empRole;

	@Column(nullable=false ,updatable = false)
	private LocalDate empDoj;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="organizationId")
	private Organization organization;
}
