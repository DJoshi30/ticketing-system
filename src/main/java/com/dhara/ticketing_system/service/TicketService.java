package com.dhara.ticketing_system.service;

import com.dhara.ticketing_system.dto.AssignAgentRequest;
import com.dhara.ticketing_system.dto.TicketRequest;
import com.dhara.ticketing_system.dto.TicketResponse;
import com.dhara.ticketing_system.entity.Ticket;
import com.dhara.ticketing_system.entity.User;
import com.dhara.ticketing_system.enums.Role;
import com.dhara.ticketing_system.enums.Status;
import com.dhara.ticketing_system.exception.InvalidAssignmentException;
import com.dhara.ticketing_system.exception.InvalidStatusTransitionException;
import com.dhara.ticketing_system.exception.ResourceNotFoundException;
import com.dhara.ticketing_system.mapper.TicketMapper;
import com.dhara.ticketing_system.repository.TicketRepository;
import com.dhara.ticketing_system.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
    }

    public TicketResponse createTicket(TicketRequest request) {
        User requester = userRepository.findById(request.getRequesterId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getRequesterId()));

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setPriority(request.getPriority());
        ticket.setRequester(requester);
        // status defaults to OPEN via @PrePersist

        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    public TicketResponse getTicketById(Long id, String requesterEmail, boolean isPrivilegedRole) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

        if (!isPrivilegedRole && !ticket.getRequester().getEmail().equals(requesterEmail)) {
            throw new AccessDeniedException("You do not have permission to view this ticket");
        }

        return ticketMapper.toResponse(ticket);
    }

    public TicketResponse assignAgent(Long ticketId, AssignAgentRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));

        User agent = userRepository.findById(request.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getAgentId()));

        if (agent.getRole() != Role.AGENT && agent.getRole() != Role.ADMIN) {
            throw new InvalidAssignmentException("User with id " + agent.getId() + " is not an AGENT or ADMIN");
        }

        ticket.setAssignedAgent(agent);
        if (ticket.getStatus() == Status.OPEN) {
            ticket.setStatus(Status.IN_PROGRESS);
        }

        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    private static final Map<Status, Set<Status>> ALLOWED_TRANSITIONS = Map.of(
            Status.OPEN, Set.of(Status.IN_PROGRESS),
            Status.IN_PROGRESS, Set.of(Status.RESOLVED, Status.OPEN),
            Status.RESOLVED, Set.of(Status.CLOSED, Status.IN_PROGRESS),
            Status.CLOSED, Set.of()
    );

    public TicketResponse updateStatus(Long ticketId, Status newStatus) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));

        Status current = ticket.getStatus();
        Set<Status> allowedNext = ALLOWED_TRANSITIONS.getOrDefault(current, Set.of());

        if (!allowedNext.contains(newStatus)) {
            throw new InvalidStatusTransitionException(
                    "Cannot move ticket from " + current + " to " + newStatus);
        }

        ticket.setStatus(newStatus);
        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    public List<TicketResponse> getAllTickets(Status status, Long assignedAgentId) {
        List<Ticket> tickets;

        if (status != null) {
            tickets = ticketRepository.findByStatus(status);
        } else if (assignedAgentId != null) {
            tickets = ticketRepository.findByAssignedAgentId(assignedAgentId);
        } else {
            tickets = ticketRepository.findAll();
        }

        return tickets.stream().map(ticketMapper::toResponse).toList();
    }

    public List<TicketResponse> getMyTickets(String email) {
        User requester = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return ticketRepository.findByRequesterId(requester.getId())
                .stream()
                .map(ticketMapper::toResponse)
                .toList();
    }
}