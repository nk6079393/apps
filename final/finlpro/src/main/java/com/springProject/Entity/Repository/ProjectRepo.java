package com.springProject.Entity.Repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springProject.Entity.Project;

import jakarta.transaction.Transactional;
@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer> {
	 @Modifying
     @Transactional
     @Query(value = "update projects set  project_end_date =:projectEndDate, project_description =:projectDescription  WHERE project_Id =:projectId ", nativeQuery = true)
	 public int updateProject(@Param("projectEndDate") LocalDate projectEndDate,
			 @Param("projectDescription") String projectDescription,@Param("projectId") Integer projectId);

	 @Query(value ="select * from projects where org_id=:id  and project_name=:projectName",nativeQuery = true)
	 public Project findByOrgIdAndProjectName(@Param("id") int orgId,@Param("projectName") String projectName);

	 @Query(value="select * from projects where org_id=:orgId",nativeQuery = true)
	 public List<Project> findByOrgId(@Param("orgId") int id);
	 
	 @Modifying
	 @Transactional
	 @Query(value="delete from project_employee where project_id=:projectId and employee_id=:empId",nativeQuery = true)
	 public int removeEmpFromProject(@Param("projectId") Integer projectId,@Param("empId")Integer empId);

	 @Modifying
	 @Transactional
	 @Query(value="update projects set project_status='inactive' where project_id=:projectId",nativeQuery = true)
	 public int closeProject(@Param("projectId") Integer projectId);
	 
	 @Query(value ="select * from projects where project_status=:status and org_id=(select org_id from organization where org_username=:orgUsername)",nativeQuery = true)
	 public List<Project>getProjectsByOrgUsernameStatus(@Param("orgUsername") String orgUsername,@Param("status") String status);

	 @Query(value="select p.project_id from projects p inner join project_employee  pe on p.project_id=pe.project_id and pe.employee_id=:empId and p.project_status='active'",nativeQuery = true)
	 public List<Integer> getProjectIdsByEmpId(@Param("empId") Integer empId);
	 
	 @Query(value="select p.project_name from projects p inner join project_employee  pe on p.project_id=pe.project_id and pe.employee_id=:empId and p.project_status='active'",nativeQuery = true)
	 public List<String> getProjectNamesByEmpId(@Param("empId") Integer empId);
	 @Query(value = "select * from projects where org_id = :id(select org_id from organization where org_username=:orgUsername) AND project_name LIKE %:projectName%", nativeQuery = true)
	 public List<Project> findByOrgIdAndProjectNamecontain(@Param("orgUsername") String orgUsername, @Param("projectName") String projectName);

}
