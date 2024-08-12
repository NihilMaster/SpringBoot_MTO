package zzz.master.REST.publisher.MTO.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zzz.master.REST.publisher.MTO.Entities.PublicationEntity;
import zzz.master.REST.publisher.MTO.Repositories.PublicationRepository;

@RestController
public class PublicationController {

    @Autowired
    private PublicationRepository publicationRepository;

    @GetMapping("/publications")
    public Page<PublicationEntity> getPublications(Pageable pageable) {
        return publicationRepository.findAll(pageable);
    }

    @PostMapping("/publications")
    public PublicationEntity addPublication(@Valid @RequestBody PublicationEntity publication) {
        return publicationRepository.save(publication);
    }

    @PutMapping("/publications/{id}")
    public PublicationEntity updatePublication(@PathVariable Long id, @Valid @RequestBody PublicationEntity publication) {
        publication.setId(id);
        return publicationRepository.findById(id).map(publicationMap -> {
            publicationMap.setTitle(publication.getTitle().isBlank() ? publication.getTitle() : publicationRepository.findById(id).get().getTitle());
            publicationMap.setDescription(publication.getDescription().isBlank() ? publication.getDescription() : publicationRepository.findById(id).get().getDescription());
            publicationMap.setContent(publication.getContent().isBlank() ? publication.getContent() : publicationRepository.findById(id).get().getContent());
            return publicationRepository.save(publicationMap);
        }).orElseThrow(() -> new IllegalArgumentException("Publication not found with id = " + id));
    }

    @DeleteMapping("/publications/{id}")
    public ResponseEntity<?> deletePublication(@PathVariable Long id) {
        return publicationRepository.findById(id).map(publication -> {
                    publicationRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.noContent().build());
    }
}