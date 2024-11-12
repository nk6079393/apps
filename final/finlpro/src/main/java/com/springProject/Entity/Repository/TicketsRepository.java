package com.springProject.Entity.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;

import com.springProject.Entity.Tickets;

public interface TicketsRepository extends JpaRepository<Tickets, Integer>{

	@Query(value = "SELECT * FROM Tickets WHERE ticket_status = :ticketStatus", nativeQuery = true)
	public List<Tickets> findTicketsByStatus(@Param("ticketStatus") String ticketStatus);


	List<Tickets> findByTicketRaisedBy(String username);


	List<Tickets> findByTicketRaisedByAndTicketStatus(String username, String status);


	List<Tickets> findByTicketAssignedTo(String username);


	List<Tickets> findByTicketAssignedToAndTicketStatus(String username, String status);
	
	@Transactional
	@Modifying
	@Query(value="update tickets set ticket_closed_time = NOW(),ticket_status='inactive' WHERE ticket_id=:ticketId ",nativeQuery = true)
	int closeTicket(@Param("ticketId")Integer ticketId);
} 