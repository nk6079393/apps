package com.springProject.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "org_id")
	private Integer orgId;
	
	@Column(name = "org_name" ,  unique = true)
	private String orgName;
	
	@Column(name = "org_contact")
	private Long orgContact;
	
	@Column(name = "org_username" , unique = true)
	private String orgUsername;
	
	@Column(name = "org_password")
	private String orgPassword;
	
	private String orgImageType;
	
	@Lob
	@Column(name = "org_image" )
	private byte[] orgImage;
	
	@JsonIgnore
	@OneToMany(mappedBy="organization", cascade=CascadeType.ALL)
	@ToString.Exclude
	List<Employee> employees;
	
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy="organization", cascade=CascadeType.ALL)
	List<Project> projects;
}
