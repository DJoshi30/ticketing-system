package com.dhara.ticketing_system.controller;

import com.dhara.ticketing_system.dto.RoleUpdateRequest;
import com.dhara.ticketing_system.dto.UserRequest;
import com.dhara.ticketing_system.dto.UserResponse;
import com.dhara.ticketing_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest request){
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserResponse> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}/role")
    public UserResponse updateRole(@PathVariable Long id, @Valid @RequestBody RoleUpdateRequest request) {
        return userService.updateRole(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    public UserResponse deactivateUser(@PathVariable Long id) {
        return userService.setActiveStatus(id, false);
    }

    @PatchMapping("/{id}/activate")
    public UserResponse activateUser(@PathVariable Long id) {
        return userService.setActiveStatus(id, true);
    }

    @PostMapping("/staff")
    public UserResponse createStaffUser(@Valid @RequestBody UserRequest request) {
        return userService.createStaffUser(request);
    }
}