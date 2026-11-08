package fi.miska.gamelibrary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fi.miska.gamelibrary.web.GameController;

@SpringBootTest
class GamelibraryApplicationTests {

	@Autowired
	private GameController gameController;

	@Test // Testataan että sovellus käynnistyy ja GameController luodaan
	void gameControllerLoads() {
		assertThat(gameController).isNotNull();
	}


}
