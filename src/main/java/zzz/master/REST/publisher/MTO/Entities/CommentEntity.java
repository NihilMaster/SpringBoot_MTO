package zzz.master.REST.publisher.MTO.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "comments")
//@JsonIgnoreProperties({"publication"})
public class CommentEntity extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String txt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "publication_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PublicationEntity publication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getTxt() {
        return txt;
    }

    public void setTxt(@NotNull String txt) {
        this.txt = txt;
    }

    public PublicationEntity getPublication() {
        return publication;
    }

    public void setPublication(PublicationEntity publication) {
        this.publication = publication;
    }
}
