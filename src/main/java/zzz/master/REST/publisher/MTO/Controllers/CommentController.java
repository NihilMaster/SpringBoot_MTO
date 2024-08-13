package zzz.master.REST.publisher.MTO.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zzz.master.REST.publisher.MTO.Entities.CommentEntity;
import zzz.master.REST.publisher.MTO.Repositories.CommentRepository;
import zzz.master.REST.publisher.MTO.Repositories.PublicationRepository;

import java.util.Date;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @GetMapping("/comments")
    public Page<CommentEntity> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @GetMapping("/comments/{id}")
    public CommentEntity getComment(@PathVariable Long id) {
        return commentRepository.findById(id).get();
    }

    @GetMapping("/comments/publication/{id}")
    public Page<CommentEntity> getCommentsByPublication(@PathVariable Long id, Pageable pageable) {
        return commentRepository.findByPublicationId(id, pageable);
    }

    @PostMapping("/comments")
    public ResponseEntity<?> addComment(@Valid @RequestBody CommentEntity comment) {
        if (comment.getPublication() == null || comment.getPublication().getId() == null) {
            return ResponseEntity.badRequest().body("Publication ID is required");
        }
        return publicationRepository.findById(comment.getPublication().getId())
                .map(publication -> {
                    comment.setPublication(publication);
                    comment.setDateCreation(new Date());
                    CommentEntity savedComment = commentRepository.save(comment);
                    return ResponseEntity.ok(savedComment);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentEntity> updateComment(@PathVariable Long id, @Valid @RequestBody CommentEntity comment) {
        comment.setId(id);
        return commentRepository.findById(id).map(commentMap -> {
            commentMap.setTxt(!comment.getTxt().isBlank() ? comment.getTxt() : commentRepository.findById(id).get().getTxt());
            commentMap.setDateModification(new Date());
            if (comment.getPublication() != null) {
                commentMap.setPublication(comment.getPublication());
            }
            return ResponseEntity.ok(commentRepository.save(commentMap));
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        return commentRepository.findById(id).map(comment -> {
            commentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }
}
