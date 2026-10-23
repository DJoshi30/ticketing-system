package com.dhara.ticketing_system.dto;

import com.dhara.ticketing_system.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateRequest {

    @NotNull(message = "Role is required")
    private Role role;
}