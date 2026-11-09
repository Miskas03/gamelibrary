package fi.miska.gamelibrary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import fi.miska.gamelibrary.web.GameController;

// Testataan controllerin ja securityn toimintaa.
@SpringBootTest // Käynnistää sovelluksen.
@AutoConfigureMockMvc // Lähettää pyynnöt ilman oikeaa selainta.
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    GameController gameController;

    @Test // Testataan että games sivulle pääsee ilman kirjautumista.
    void gamesPageIsPublic() throws Exception {
        // Lähetetään GET pyyntö /games osoitteeseen, kirjautumattomana käyttäjänä.
        mockMvc.perform(get("/games"))
                // Tarkistetaan että vastauskoodi on 200 eli isOk.
                .andExpect(status().isOk());
    }

    @Test // Testataan että kirjautumattomana, admin osoite ohjaa login sivulle.
    void addgameRedirect() throws Exception {
        // Yritetään avata admin sivu kirjautumatta.
        mockMvc.perform(get("/admin/games/addgame"))
                // Tarkastetaan että vastaus on uudelleenohjaus.
                .andExpect(status().is3xxRedirection())
                // Tarkistetaan että ohjaus menee login sivulle.
                .andExpect(redirectedUrl("/login"));
    }

    @Test // Testataan että Adminina kirjautuneena pääsee addgame sivulle
    @WithMockUser (authorities = "ADMIN") // "Kirjaudutaan" admin käyttäjänä
    void addgameAsAdmin() throws Exception{
        // Avataan addgame sivu admin käyttäjänä.
        mockMvc.perform(get("/admin/games/addgame"))
        // Tarkistetaan että vastauskoodi on 200 eli isOk eli admin pääsee sivulle
        .andExpect(status().isOk())
        
        // Tarkistetaan että controller palautti addgame templaten.
        .andExpect(view().name("addgame"))

        // Tarkistetaan että modelissa on kaikki mitä addgame sivu tarvitsee.
        .andExpect(model().attributeExists("game", "developers", "genres", "platforms"));
    }
}
