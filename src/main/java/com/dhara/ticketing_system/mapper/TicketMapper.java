package com.dhara.ticketing_system.mapper;

import com.dhara.ticketing_system.dto.TicketResponse;
import com.dhara.ticketing_system.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    private final UserMapper userMapper;

    public TicketMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public TicketResponse toResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setCategory(ticket.getCategory());
        response.setPriority(ticket.getPriority());
        response.setStatus(ticket.getStatus());
        response.setRequester(userMapper.toResponse(ticket.getRequester()));
        response.setAssignedAgent(
                ticket.getAssignedAgent() != null ? userMapper.toResponse(ticket.getAssignedAgent()) : null
        );
        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());
        return response;
    }
}