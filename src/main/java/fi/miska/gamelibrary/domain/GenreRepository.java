package fi.miska.gamelibrary.domain;

import org.springframework.data.repository.CrudRepository;

// Repositorio genrejen tallentamiseen ja hakemiseen.
public interface GenreRepository extends CrudRepository<Genre, Long> {


}