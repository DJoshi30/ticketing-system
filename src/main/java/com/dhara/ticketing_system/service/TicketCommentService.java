package com.dhara.ticketing_system.service;

import com.dhara.ticketing_system.dto.CommentRequest;
import com.dhara.ticketing_system.dto.CommentResponse;
import com.dhara.ticketing_system.entity.Ticket;
import com.dhara.ticketing_system.entity.TicketComment;
import com.dhara.ticketing_system.entity.User;
import com.dhara.ticketing_system.exception.ResourceNotFoundException;
import com.dhara.ticketing_system.mapper.CommentMapper;
import com.dhara.ticketing_system.repository.TicketCommentRepository;
import com.dhara.ticketing_system.repository.TicketRepository;
import com.dhara.ticketing_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketCommentService {

    private final TicketCommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public TicketCommentService(TicketCommentRepository commentRepository,
                                TicketRepository ticketRepository,
                                UserRepository userRepository,
                                CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponse addComment(Long ticketId, CommentRequest request, String authorEmail) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));

        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + authorEmail));

        TicketComment comment = new TicketComment();
        comment.setContent(request.getContent());
        comment.setTicket(ticket);
        comment.setAuthor(author);

        return commentMapper.toResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> getCommentsForTicket(Long ticketId) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new ResourceNotFoundException("Ticket not found with id: " + ticketId);
        }

        return commentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }
}