package fi.miska.gamelibrary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import fi.miska.gamelibrary.domain.Developer;
import fi.miska.gamelibrary.domain.DeveloperRepository;
import fi.miska.gamelibrary.domain.Game;
import fi.miska.gamelibrary.domain.GameRepository;
import fi.miska.gamelibrary.domain.Genre;
import fi.miska.gamelibrary.domain.GenreRepository;
import fi.miska.gamelibrary.domain.Platform;
import fi.miska.gamelibrary.domain.PlatformRepository;


// Käynnistetään vain tietokanta osuus koodista.
@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Test // Tarkistetaan tallentuiko peli, tarkistamalla löytyykö testGame oliolta id:tä.
    void createNewGame() {

        // Luodaan testin ajaksi data jota game tarvitsee.
        Developer testDev = developerRepository.save(new Developer("Test dev"));
        Platform testPlat = platformRepository.save(new Platform("Test platform"));
        Genre testGenre = genreRepository.save(new Genre("Test genre"));

        // Suoritetaan eli tallennetaan peli. Tämä on se testattava asia.
        Game testGame = gameRepository.save(new Game("Test Game", testDev, "Test info",
                LocalDate.of(2022, 2, 25), List.of(testPlat), List.of(testGenre)));

        // Tarkistetaan assertThat:in avulla, onko tulos oikea.
        assertThat(testGame.getId()).isNotNull();

    }

    @Test // Testataan löytyykö tallennettu peli sen id:llä.
    void findGameById() {

        // Luodaan testin ajaksi data jota game tarvitsee.
        Developer testDev = developerRepository.save(new Developer("Test dev"));
        Platform testPlat = platformRepository.save(new Platform("Test platform"));
        Genre testGenre = genreRepository.save(new Genre("Test genre"));

        // Tallennetaan peli valmiiksi, jolloin se saa id:n.
        Game testGame = gameRepository.save(new Game("Test Game", testDev, "Test info",
                LocalDate.of(2022, 2, 25), List.of(testPlat), List.of(testGenre)));

        // Haetaan peli sen id:llä. Optional sisältää pelin tai on tyhjä.
        Optional<Game> foundGame = gameRepository.findById(testGame.getId());

        // Tarkistetaan että Optionalissa on peli eli peli löytyi.
        assertThat(foundGame).isPresent();
    }

    @Test // Testataan löytyykö tallennettu peli sen nimellä.
    void findGameByName() {

        // Luodaan testin ajaksi data jota game tarvitsee.
        Developer testDev = developerRepository.save(new Developer("Test dev"));
        Platform testPlat = platformRepository.save(new Platform("Test platform"));
        Genre testGenre = genreRepository.save(new Genre("Test genre"));

        // Tallennetaan peli testinimellä jota ei ole jo testidatassa.
        Game testGame = gameRepository.save(new Game("Test game", testDev, "Test info",
                LocalDate.of(2022, 2, 25), List.of(testPlat), List.of(testGenre)));

        // Haetaan peli sille annetulla nimellä.
        List<Game> foundGame = gameRepository.findByName("Test game");

        // Tarkistetaan että listalla on tasan yksi peli ja sen id täsmää tallennetun
        // id:seen.
        assertThat(foundGame).hasSize(1);
        assertThat(foundGame.get(0).getId().equals(testGame.getId())).isTrue();
    }

    @Test // Testataan että peli poistuu tietokannasta
    void deleteGame() {

        // Luodaan testin ajaksi data jota game tarvitsee.
        Developer testDev = developerRepository.save(new Developer("Test dev"));
        Platform testPlat = platformRepository.save(new Platform("Test platform"));
        Genre testGenre = genreRepository.save(new Genre("Test genre"));

        // Tallennetaan peli, jolloin se saa id:n.
        Game testGame = gameRepository.save(new Game("Test game", testDev, "Test info",
                LocalDate.of(2022, 2, 25), List.of(testPlat), List.of(testGenre)));

        // Haetaan peli sen id:llä.
        Optional<Game> gameBeforeDelete = gameRepository.findById(testGame.getId());

        // Tarkistetaan että peli löytyy ennen poistoa.
        assertThat(gameBeforeDelete).isPresent();

        // Poistetaan peli id:n perusteella.
        gameRepository.deleteById(testGame.getId());


        // Haetaan peli sen id:llä.
        Optional<Game> gameAfterDelete = gameRepository.findById(testGame.getId());

        // Tarkistetaan palauttaako Optional on tyhjä eli peli on poistettu
        assertThat(gameAfterDelete).isEmpty();

    }

}
