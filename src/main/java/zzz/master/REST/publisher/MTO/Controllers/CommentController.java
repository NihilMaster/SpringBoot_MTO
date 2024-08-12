package zzz.master.REST.publisher.MTO.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zzz.master.REST.publisher.MTO.Entities.CommentEntity;
import zzz.master.REST.publisher.MTO.Repositories.CommentRepository;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/comments")
    public Page<CommentEntity> getPublications(Pageable pageable) {
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
    public CommentEntity addComment(@Valid @RequestBody CommentEntity comment) {
        return commentRepository.save(comment);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentEntity> updateComment(@PathVariable Long id, @Valid @RequestBody CommentEntity comment) {
        comment.setId(id);
        return !commentRepository.existsById(id) ?
                ResponseEntity.ok(commentRepository.save(comment)) :
                ResponseEntity.notFound().build();
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
