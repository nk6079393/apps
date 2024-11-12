package com.springProject.Entity.ServiceLayer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.springProject.OrganizationDTO.TicketsDTO;

public interface TicketsService {

	public ResponseEntity<String> insertAllTickets(TicketsDTO ticketsDTO);
	public List<TicketsDTO> getAllTickets();
	public ResponseEntity<List<TicketsDTO>> getAllActiveTickets(String ticketStatus);
	public ResponseEntity<String> ClosedTickets(int ticketId);
	public ResponseEntity<List<TicketsDTO>> getAllInActiveTickets();
	public ResponseEntity<List<TicketsDTO>> getTicketsByEmpUsernameAndRole(String username,String status);
	ResponseEntity<String> closeTicket(Integer ticketId);
	
}
