package com.dhara.ticketing_system.service;

import com.dhara.ticketing_system.dto.RoleUpdateRequest;
import com.dhara.ticketing_system.dto.UserRequest;
import com.dhara.ticketing_system.dto.UserResponse;
import com.dhara.ticketing_system.entity.User;
import com.dhara.ticketing_system.enums.Role;
import com.dhara.ticketing_system.exception.DuplicateEmailException;
import com.dhara.ticketing_system.exception.ResourceNotFoundException;
import com.dhara.ticketing_system.mapper.UserMapper;
import com.dhara.ticketing_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already in use: " + request.getEmail());
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER); // signup always creates a CUSTOMER

        return userMapper.toResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    public UserResponse updateRole(Long id, RoleUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setRole(request.getRole());
        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse setActiveStatus(Long id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setActive(active);
        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse createStaffUser(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already in use: " + request.getEmail());
        }
        if (request.getRole() == Role.CUSTOMER) {
            throw new IllegalArgumentException("Use /users to create customer accounts");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userMapper.toResponse(userRepository.save(user));
    }
}