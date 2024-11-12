package com.springProject.OrganizationDTO;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.springProject.Entity.Project;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class TicketsDTO {

	private Integer ticketId;
	private String ticketType;
	private String ticketDescription;
	private String ticketRaisedBy;
	private String ticketAssignedTo;
	private String ticketStatus;  //active and inactive
	private LocalDateTime ticketClosedTime; //when developer closed the ticket automatically it set the time	
    private Project project;
    private LocalDateTime ticketRaisedDate;

}
