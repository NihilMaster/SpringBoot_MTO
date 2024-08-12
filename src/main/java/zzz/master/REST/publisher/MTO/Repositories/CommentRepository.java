package zzz.master.REST.publisher.MTO.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zzz.master.REST.publisher.MTO.Entities.CommentEntity;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findByPublicationId (Long publicationId, Pageable pageable);
    Optional<CommentEntity> findByIdAndPublicationId (Long commentId, Long publicationId);
}
