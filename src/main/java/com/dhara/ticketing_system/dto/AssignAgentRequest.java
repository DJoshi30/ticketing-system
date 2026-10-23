package com.dhara.ticketing_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignAgentRequest {

    @NotNull(message = "Agent ID is required")
    private Long agentId;
}