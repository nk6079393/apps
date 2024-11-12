package com.springProject.Entity.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.springProject.Entity.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee,Integer>{


  Optional<Employee> findByEmpUsername(String username);
  
  @Query(value="select emp_role from employees where emp_username=:username and emp_password=:password",nativeQuery = true)
  String findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

  @Query(value="select * from employees where emp_username=:username and organization_id=:id",nativeQuery = true)
  Employee findByEmpUsernameAndOrgId(String username,int id);

  Employee findByEmpContact(Long empContact);
  
  @Query(value="select e.emp_username from employees e inner join project_employee pe on e.emp_id=pe.employee_id and pe.project_id=:id and e.emp_role='developer'",nativeQuery = true)
  List<String> getEmpNamesByProjectId(@Param("id") Integer id);

  @Query(value="select emp_role from employees where emp_username=:username",nativeQuery = true)
  String getRoleByEmpUsername(@Param("username") String username);
}
