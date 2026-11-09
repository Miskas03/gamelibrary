package fi.miska.gamelibrary;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.miska.gamelibrary.domain.Developer;
import fi.miska.gamelibrary.domain.DeveloperRepository;
import fi.miska.gamelibrary.domain.Game;
import fi.miska.gamelibrary.domain.GameRepository;
import fi.miska.gamelibrary.domain.Genre;
import fi.miska.gamelibrary.domain.GenreRepository;
import fi.miska.gamelibrary.domain.Platform;
import fi.miska.gamelibrary.domain.PlatformRepository;
import fi.miska.gamelibrary.domain.User;
import fi.miska.gamelibrary.domain.UserRepository;


// Sovelluksen pääluokka. Tämän alta spring löytää kaikki controllerit, repositoriot ja entiteetit.
@SpringBootApplication
public class GamelibraryApplication {

	// Ohjelman aloituskohta josta spring käynnistyy ja Tomcat palvelin käynnistyy osoitteeseen localhost:8080.
	// Spring luo repositoriot ja controllerit, JPA luo taulut entiteeteistä.
	public static void main(String[] args) { 
		SpringApplication.run(GamelibraryApplication.class, args);
	}

	// Luodaan testidata joka käynnistyksellä sillä H2 tietokanta on tallennettu muistiin ja se tyhjenee kun sovellus sammuu.
	// Spring luo tämän olion käynnistyksessä (Bean).
	//Spring ajaa tämän automaattisesti kun sovellus on käynnistynyt (CommandLineRunner).
	@Bean
	public CommandLineRunner testData(GenreRepository genreRepository, PlatformRepository platformRepository,
			DeveloperRepository developerRepository, GameRepository gameRepository, UserRepository userRepository) {
		return (args) -> {

			// BCrypt hashaa salasanat jotta tietokantaan ei tallennu selväkielisiä salasanoja.
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			// Tallennetaan kaksi testi käyttäjää. Käyttäjätunnus, salasana joka hashataan, rooli.
			userRepository.save(new User("user", encoder.encode("user"), "USER"));
			userRepository.save(new User("admin", encoder.encode("admin"), "ADMIN"));

			// Tallennetaan ensin genre-, platform- ja developer-oliot jotta niillä on id:t, kun pelit viittaavat niihin.
			// save palauttaa tallennetun olion joka otetaan muuttujaan.
			Genre rpg = genreRepository.save(new Genre("RPG"));
			Genre action = genreRepository.save(new Genre("ACTION"));
			Genre fps = genreRepository.save(new Genre("FPS"));
			Genre puzzle = genreRepository.save(new Genre("PUZZLE"));

			Platform pc = platformRepository.save(new Platform("PC"));
			Platform ps5 = platformRepository.save(new Platform("PS5"));
			Platform xboxSeriesS = platformRepository.save(new Platform("XBOX SERIES S"));

			// Unknown developer vaihtoehtona jos developeriä ei tiedetä mutta halutaan tallentaa peli.
			Developer unknownDeveloper = developerRepository.save(new Developer("Unknown developer"));
			Developer fromSoftware = developerRepository.save(new Developer("fromSoftware"));
			Developer electronicArts = developerRepository.save(new Developer("Electronic Arts"));

			//Tallennetaan kaksi testipeliä. Konstruktori saa tiedot genreen, platformiin ja developeriin aikaisemmin tallennetuista olioista.
			gameRepository.save(new Game("Elden Ring", fromSoftware, "Open world action RPG",
					LocalDate.of(2022, 2, 25), List.of(pc, ps5), List.of(rpg, action)));

			gameRepository.save(new Game("Battlefield", electronicArts, "Shooter game",
					LocalDate.of(2011, 10, 25), List.of(pc), List.of(fps, action)));

		};
	}

}
