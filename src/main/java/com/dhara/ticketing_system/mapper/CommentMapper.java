package com.dhara.ticketing_system.mapper;

import com.dhara.ticketing_system.dto.CommentResponse;
import com.dhara.ticketing_system.entity.TicketComment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public CommentResponse toResponse(TicketComment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setAuthor(userMapper.toResponse(comment.getAuthor()));
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }
}