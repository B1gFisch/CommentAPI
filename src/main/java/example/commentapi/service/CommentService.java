package example.commentapi.service;

import example.commentapi.model.Comment;
import example.commentapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private final List<String> randomNames = List.of("BlueFish", "RedFox", "GreenWolf", "YellowBee");
    private final Map<String, Set<Long>> sessionLikes = new HashMap<>();

    public Comment createComment(String content, String sessionId) {
        String username = randomNames.get(new Random().nextInt(randomNames.size()));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUsername(username);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment likeComment(Long id, String sessionId) {

        sessionLikes.putIfAbsent(sessionId, new HashSet<>());
        if (!sessionLikes.get(sessionId).contains(id)) {
            Comment comment = commentRepository.findById(id).orElseThrow();
            comment.setLikes(comment.getLikes() + 1);
            sessionLikes.get(sessionId).add(id);
            return commentRepository.save(comment);
        }
        return commentRepository.findById(id).orElseThrow();
    }

    public Comment dislikeComment(Long id, String sessionId) {
        sessionLikes.putIfAbsent(sessionId, new HashSet<>());
        if (!sessionLikes.get(sessionId).contains(id)) {
            Comment comment = commentRepository.findById(id).orElseThrow();
            comment.setDislikes(comment.getDislikes() + 1);
            sessionLikes.get(sessionId).add(id);
            return commentRepository.save(comment);
        }
        return commentRepository.findById(id).orElseThrow();
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }
}