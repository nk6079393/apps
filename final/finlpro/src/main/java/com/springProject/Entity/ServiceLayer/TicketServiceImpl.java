package com.springProject.Entity.ServiceLayer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springProject.Entity.Employee;
import com.springProject.Entity.Tickets;
import com.springProject.Entity.Repository.EmployeeRepository;
import com.springProject.Entity.Repository.TicketsRepository;
import com.springProject.OrganizationDTO.TicketsDTO;

@Service
public class TicketServiceImpl implements TicketsService{

	@Autowired
	private TicketsRepository ticketsRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Override
	public ResponseEntity<String> insertAllTickets(TicketsDTO ticketsDTO) {
		Tickets tickets = modelMapper.map(ticketsDTO, Tickets.class);
		tickets.setTicketStatus("active");
		Tickets savedTickets = ticketsRepository.save(tickets);
		TicketsDTO savedTicketsDTO = modelMapper.map(savedTickets, TicketsDTO.class);
		if(savedTicketsDTO != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("data saved");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@Override
	public List<TicketsDTO> getAllTickets() {
		
		return ticketsRepository.findAll().stream()
				.map(tic->modelMapper.map(tic, TicketsDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<List<TicketsDTO>> getAllActiveTickets(String ticketStatus) {
	    List<Tickets> tickets = ticketsRepository.findTicketsByStatus(ticketStatus);
	    if (!tickets.isEmpty()) {
	        List<TicketsDTO> ticketDTOs = tickets.stream()
	            .map(ticket -> modelMapper.map(ticket, TicketsDTO.class))
	            .collect(Collectors.toList());
	        return ResponseEntity.ok(ticketDTOs);
	    } else {
	        throw new RuntimeException("No tickets found with the specified status");
	    }
	}

	@Override
	public ResponseEntity<String> ClosedTickets(int ticketId) {
	    Tickets ticket = ticketsRepository.findById(ticketId)
	            .orElseThrow(() -> new RuntimeException("Ticket not found"));
	    ticket.setTicketStatus("inactive");
	    ticket.setTicketClosedTime(LocalDateTime.now());
	    ticketsRepository.save(ticket);

	    return ResponseEntity.ok("Ticket is closed successfully");
	}

	@Override
	public ResponseEntity<List<TicketsDTO>> getAllInActiveTickets() {
		List<Tickets> inActiveTickets = ticketsRepository.findTicketsByStatus("inactive"); 
		if (!inActiveTickets.isEmpty()) {
	        List<TicketsDTO> ticketDTOs = inActiveTickets.stream()
	            .map(ticket -> modelMapper.map(ticket, TicketsDTO.class))
	            .collect(Collectors.toList());
	        return ResponseEntity.ok(ticketDTOs);
	    } else {
	        throw new RuntimeException("No tickets found with the specified status");
	    }
	}

	@Override
	public ResponseEntity<List<TicketsDTO>> getTicketsByEmpUsernameAndRole(String username,String status) {
		
		Employee emp=empRepo.findByEmpUsername(username).get();
		
		if(emp.getEmpRole().equals("tester")) {
			if(status.equals("all")) {
				List<Tickets> tickets=ticketsRepository.findByTicketRaisedBy(username);
				if(tickets.size()>0) {
					return ResponseEntity.ok(tickets.stream().map(ticket->modelMapper.map(ticket, TicketsDTO.class)).toList());
				}
			}else {
				List<Tickets> tickets=ticketsRepository.findByTicketRaisedByAndTicketStatus(username,status);
				if(tickets.size()>0) {
					return ResponseEntity.ok(tickets.stream().map(ticket->modelMapper.map(ticket, TicketsDTO.class)).toList());
				}
			}
		}else {
			if(status.equals("all")) {
				List<Tickets> tickets=ticketsRepository.findByTicketAssignedTo(username);
				if(tickets.size()>0) {
					return ResponseEntity.ok(tickets.stream().map(ticket->modelMapper.map(ticket, TicketsDTO.class)).toList());
				}
			}else {
				List<Tickets> tickets=ticketsRepository.findByTicketAssignedToAndTicketStatus(username,status);
				if(tickets.size()>0) {
					return ResponseEntity.ok(tickets.stream().map(ticket->modelMapper.map(ticket, TicketsDTO.class)).toList());
				}
			}
		}
		
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<String> closeTicket(Integer ticketId) {
		
		Optional<Tickets> ticket = ticketsRepository.findById(ticketId);
		if(ticket.isPresent()) {
			ticketsRepository.closeTicket(ticketId);
			return ResponseEntity.accepted().body("closed");
		}
		return ResponseEntity.noContent().build();
	}
	
	
}