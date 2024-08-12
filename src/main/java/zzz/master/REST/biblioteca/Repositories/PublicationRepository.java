package zzz.master.REST.biblioteca.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zzz.master.REST.biblioteca.Entities.PublicationEntity;

public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {

}
