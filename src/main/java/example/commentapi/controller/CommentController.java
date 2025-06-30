package example.commentapi.controller;


import example.commentapi.model.Comment;
import example.commentapi.model.CommentRequest;
import example.commentapi.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest request, HttpServletRequest http) {
        return ResponseEntity.ok(commentService.createComment(request.getContent(), http.getSession().getId()));
    }



    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Comment> likeComment(@PathVariable Long id, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return ResponseEntity.ok(commentService.likeComment(id, sessionId));
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Comment> dislikeComment(@PathVariable Long id, HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return ResponseEntity.ok(commentService.dislikeComment(id, sessionId));
    }
}
