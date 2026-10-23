package com.dhara.ticketing_system.repository;

import com.dhara.ticketing_system.entity.Ticket;
import com.dhara.ticketing_system.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByRequesterId(Long requesterId);
    List<Ticket> findByAssignedAgentId(Long agentId);
    List<Ticket> findByStatus(Status status);
    List<Ticket> findByRequesterIdAndStatus(Long requesterId, Status status);
}