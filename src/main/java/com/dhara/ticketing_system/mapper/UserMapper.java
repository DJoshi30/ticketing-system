package com.dhara.ticketing_system.mapper;

import com.dhara.ticketing_system.dto.UserResponse;
import com.dhara.ticketing_system.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        );
    }
}