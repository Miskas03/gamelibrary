package fi.miska.gamelibrary.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


// Repositorio pelien tallentamiseen ja hakemiseen tietokannasta.
// CrudRepository antaa valmiit metodit kuten save ja findAll
// Game = entiteetti, Long sen id:n tyyppi.
public interface GameRepository extends CrudRepository<Game, Long>{

    //Hakee pelit nimellä. Spring kysely menee findBy + kenttä name.
    // Palauttaa listan.
    List<Game> findByName(String name);
}