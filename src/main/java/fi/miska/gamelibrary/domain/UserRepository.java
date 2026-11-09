package fi.miska.gamelibrary.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


// Repositorio käyttäjien hakemiseen.
// exported = false: Spring Data REST ei tee tästä /api osoitetta,
//jossa olisivat käyttäjätunnukset ja salasanahashit.
@RepositoryRestResource (exported = false)

// Hakee käyttän käyttäjätunnuksella kirjautumista varten (UserDetailServiceImpl).
// Palauttaa yhden käyttäjän tai null koska usernamet ovat uniikkeja.
public interface UserRepository extends CrudRepository<User, Long>{
    User findByUsername(String username);
}
