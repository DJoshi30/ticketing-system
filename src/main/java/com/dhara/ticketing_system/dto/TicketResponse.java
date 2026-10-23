package com.dhara.ticketing_system.dto;

import com.dhara.ticketing_system.enums.Category;
import com.dhara.ticketing_system.enums.Priority;
import com.dhara.ticketing_system.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private Category category;
    private Priority priority;
    private Status status;
    private UserResponse requester;
    private UserResponse assignedAgent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}