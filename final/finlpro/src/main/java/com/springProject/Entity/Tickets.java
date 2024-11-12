package com.springProject.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Tickets {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ticketId;
	private String ticketType;
	private String ticketDescription;
	private String ticketRaisedBy;
	private String ticketAssignedTo;
	private String ticketStatus;  //active and inactive
	
	@CreationTimestamp
	@Column(updatable = false )
	private LocalDateTime ticketRaisedDate; //tester will set date and time
	
	private LocalDateTime ticketClosedTime; //when developer closed the ticket automatically it set the time	

	@ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
