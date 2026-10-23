package com.dhara.ticketing_system.dto;

import com.dhara.ticketing_system.enums.Category;
import com.dhara.ticketing_system.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 150)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000)
    private String description;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Requester ID is required")
    private Long requesterId;
}