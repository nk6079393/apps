package com.springProject.Entity.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springProject.Entity.Organization;


public interface OrganizationRepository extends JpaRepository<Organization , Integer> {
	
    @Query("SELECT o FROM Organization o WHERE o.orgUsername = :orgUsername AND o.orgPassword = :orgPassword")
    public Organization findByUserNameAndOrgPasswordUsingQuery(@Param("orgUsername") String orgUsername, @Param("orgPassword") String orgPassword);
  
    @Query("SELECT o FROM Organization o WHERE o.orgUsername = :orgUsername AND o.orgContact = :orgContact")
    public Organization forgotPassword(@Param("orgUsername") String orgUsername , @Param("orgContact") Long orgContact);

    @Query(value="SELECT org_name FROM Organization",nativeQuery = true)
	public List<String> getAllNames();
    
    @Query(value="SELECT org_username FROM Organization",nativeQuery = true)
    public List<String> getAllUsernames();
    
    @Query("SELECT o FROM Organization o WHERE o.orgUsername = :username")
    public Organization findByUsername(@Param("username") String username);

	public Organization findByOrgUsername(String username);

	@Query(value="select o.org_name from organization o where o.org_id=(select e.organization_id from employees e where e.emp_username=:username)",nativeQuery = true)
	String getOrgNameByEmpUsername(@Param("username") String empUsername);
}

