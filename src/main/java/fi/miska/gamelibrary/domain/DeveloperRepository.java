package fi.miska.gamelibrary.domain;

import org.springframework.data.repository.CrudRepository;

// Repositorio developereiden tallentamiseen ja hakemiseen.
public interface DeveloperRepository extends CrudRepository<Developer, Long> {

}
