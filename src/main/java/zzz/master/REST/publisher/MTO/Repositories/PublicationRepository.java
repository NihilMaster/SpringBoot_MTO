package zzz.master.REST.publisher.MTO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zzz.master.REST.publisher.MTO.Entities.PublicationEntity;

public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {

}
