package fi.miska.gamelibrary;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.miska.gamelibrary.domain.Developer;
import fi.miska.gamelibrary.domain.DeveloperRepository;
import fi.miska.gamelibrary.domain.Game;
import fi.miska.gamelibrary.domain.GameRepository;
import fi.miska.gamelibrary.domain.Genre;
import fi.miska.gamelibrary.domain.GenreRepository;
import fi.miska.gamelibrary.domain.Platform;
import fi.miska.gamelibrary.domain.PlatformRepository;

@SpringBootApplication
public class GamelibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamelibraryApplication.class, args);
	}

	//TESTI DATA TESTAUSTA VARTEN
	@Bean
	public CommandLineRunner testData(GenreRepository genreRepository, PlatformRepository platformRepository,
		DeveloperRepository developerRepository, GameRepository gameRepository) {
			return (args) ->{

				// Tallennetaan ensin genre, platform ja dev jotta game voi napata ne mukaan
				Genre rpg = genreRepository.save(new Genre("RPG"));
				Genre action = genreRepository.save(new Genre("ACTION"));
				Genre fps = genreRepository.save(new Genre("FPS"));
				Genre puzzle = genreRepository.save(new Genre("PUZZLE"));

				Platform pc = platformRepository.save(new Platform("PC"));
				Platform PS5 = platformRepository.save(new Platform("PS5"));

				Developer fromSoftware = developerRepository.save(new Developer("fromSoftware"));
				Developer electronicArts = developerRepository.save(new Developer("Electronic Arts"));
				gameRepository.save(new Game("Elden Ring", fromSoftware, "Open world action RPG",
				 LocalDate.of(2022, 2, 25), List.of(pc,PS5), List.of(rpg,action)));

				gameRepository.save(new Game("Battlefield", electronicArts, "Shooter game",
				 LocalDate.of(2011, 10, 25), List.of(pc), List.of(fps,action)));

			};
		}

}
