package example.commentapi.controller;


import example.commentapi.model.Comment;
import example.commentapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CommentService commentService;

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @PostMapping("/comments")
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }
}