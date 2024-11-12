package com.springProject.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springProject.Entity.ServiceLayer.TicketServiceImpl;
import com.springProject.OrganizationDTO.TicketsDTO;
@CrossOrigin(origins = {"http://localhost:56999", "http://localhost:4200"}) // Correct format
@RestController
@RequestMapping("/api/ticket")
public class TicketsController {

	@Autowired
	private TicketServiceImpl ticketServiceImpl;
	
	@PostMapping("/add-ticket")
	public ResponseEntity<String> postAllData(@RequestBody TicketsDTO ticketsDTO) {

		return ticketServiceImpl.insertAllTickets(ticketsDTO);
	}
	
	@GetMapping("/get-all-tickets")
	public List<TicketsDTO> getAllTickets() {
		return ticketServiceImpl.getAllTickets();
	}
	
	@GetMapping("/active")
	public ResponseEntity<List<TicketsDTO>> getAllActiveTickets(@RequestParam(required = false, defaultValue = "active") String ticketStatus) {
	    return ticketServiceImpl.getAllActiveTickets(ticketStatus);
	}
	
	@PutMapping("/{ticketid}")
	public ResponseEntity<String> closeTicket(@PathVariable  int ticketid) {
	    return ticketServiceImpl.ClosedTickets(ticketid);
	}
	
	@GetMapping("/{inactive}")
	public ResponseEntity<List<TicketsDTO>> getAllInActiveDetails(){
		return ticketServiceImpl.getAllInActiveTickets();
	}
	
	@GetMapping("/get-tickets-by/{empUsername}/{status}")
	public ResponseEntity<List<TicketsDTO>> getTicketsBy(@PathVariable String empUsername,@PathVariable String status) {
		return ticketServiceImpl.getTicketsByEmpUsernameAndRole(empUsername,status);
	}
	
	@DeleteMapping("/close-ticket/{ticketId}")
	public ResponseEntity<String> closeTicket(@PathVariable Integer ticketId){
		return ticketServiceImpl.closeTicket(ticketId);
	}
	
}