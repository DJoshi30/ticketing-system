package com.dhara.ticketing_system.controller;

import com.dhara.ticketing_system.dto.AssignAgentRequest;
import com.dhara.ticketing_system.dto.TicketRequest;
import com.dhara.ticketing_system.dto.TicketResponse;
import com.dhara.ticketing_system.enums.Status;
import com.dhara.ticketing_system.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public TicketResponse createTicket(@Valid @RequestBody TicketRequest request) {
        return ticketService.createTicket(request);
    }

    @GetMapping
    public List<TicketResponse> getAllTickets(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long assignedAgentId) {
        return ticketService.getAllTickets(status, assignedAgentId);
    }

    @GetMapping("/my")
    public List<TicketResponse> getMyTickets(Authentication authentication) {
        String email = authentication.getName();
        return ticketService.getMyTickets(email);
    }

    @GetMapping("/{id}")
    public TicketResponse getTicketById(@PathVariable Long id, Authentication authentication) {
        boolean isPrivileged = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_AGENT"));
        return ticketService.getTicketById(id, authentication.getName(), isPrivileged);
    }

    @PatchMapping("/{id}/assign")
    public TicketResponse assignAgent(@PathVariable Long id, @Valid @RequestBody AssignAgentRequest request) {
        return ticketService.assignAgent(id, request);
    }

    @PatchMapping("/{id}/status")
    public TicketResponse updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return ticketService.updateStatus(id, status);
    }
}