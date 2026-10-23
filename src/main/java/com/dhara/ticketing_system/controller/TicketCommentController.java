package com.dhara.ticketing_system.controller;

import com.dhara.ticketing_system.dto.CommentRequest;
import com.dhara.ticketing_system.dto.CommentResponse;
import com.dhara.ticketing_system.service.TicketCommentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets/{ticketId}/comments")
public class TicketCommentController {

    private final TicketCommentService commentService;

    public TicketCommentController(TicketCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentResponse addComment(@PathVariable Long ticketId,
                                      @Valid @RequestBody CommentRequest request,
                                      Authentication authentication) {
        String authorEmail = authentication.getName();
        return commentService.addComment(ticketId, request, authorEmail);
    }

    @GetMapping
    public List<CommentResponse> getComments(@PathVariable Long ticketId) {
        return commentService.getCommentsForTicket(ticketId);
    }
}