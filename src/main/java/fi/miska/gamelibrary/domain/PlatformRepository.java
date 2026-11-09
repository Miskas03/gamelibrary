package fi.miska.gamelibrary.domain;

import org.springframework.data.repository.CrudRepository;

// Repositorio pelien tallentamiseen ja hakemiseen.
public interface PlatformRepository extends CrudRepository<Platform, Long> {

    
}
