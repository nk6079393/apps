package com.springProject.Entity;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springProject.OrganizationDTO.EmployeeDto;
import com.springProject.OrganizationDTO.EmployeeResponseDto;


@Configuration
public class AppConfig {
	
	@Bean
	public ModelMapper modelMapper()
	{
		ModelMapper modelMapper = new ModelMapper();		
		modelMapper.typeMap(Employee.class, EmployeeDto.class);
		modelMapper.typeMap(EmployeeDto.class, Employee.class);
		modelMapper.typeMap(Employee.class, EmployeeResponseDto.class);
		return new ModelMapper();
	}



}
