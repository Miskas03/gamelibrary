package fi.miska.gamelibrary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

// Testataan controllerin ja securityn toimintaa.
@SpringBootTest // Käynnistää sovelluksen.
@AutoConfigureMockMvc // Lähettää pyynnöt "valeselaimessa" ilman oikeaa selainta.
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test // Testataan että games sivulle pääsee ilman kirjautumista.
    void gamesPageIsPublic() throws Exception {
        // Lähetetään GET pyyntö /games osoitteeseen, kirjautumattomana käyttäjänä.
        mockMvc.perform(get("/games"))
                // Tarkistetaan että vastauskoodi on 200 eli isOk.
                .andExpect(status().isOk());
    }

    @Test // Testataan että api/games sivulle pääsee ilman kirjautumista.
    void apiGamesIsPublic() throws Exception {
        // Lähetetään GET pyyntö /api/games osoitteeseen, kirjautumattomana käyttäjänä.
        mockMvc.perform(get("/api/games"))
                // Tarkistetaan että vastauskoodi on 200 eli isOk.
                .andExpect(status().isOk());
    }

    @Test // Testataan että api/games sivulle ei pääse lähettämään post pyyntöjä user
          // käyttäjänä.
    @WithMockUser(authorities = "USER") // "Kirjaudutan" user käyttäjänä
    void apiGamesUserCantPost() throws Exception {
        // Lähetetään POST pyyntö /api/games osoitteeseen, user käyttäjänä.
        mockMvc.perform(post("/api/games").with(csrf())) // Lisätään pyyntöön csrf token, niinkuin thymeleaf lisäisi
                                                         // oikeisiin lomakkeisiin.
                // Tarkistetaan että vastauskoodi on 403 eli isForbidden.
                .andExpect(status().isForbidden());
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
    @WithMockUser(authorities = "ADMIN") // "Kirjaudutaan" admin käyttäjänä
    void addgameAsAdmin() throws Exception {
        // Avataan addgame sivu admin käyttäjänä.
        mockMvc.perform(get("/admin/games/addgame"))
                // Tarkistetaan että vastauskoodi on 200 eli isOk eli admin pääsee sivulle
                .andExpect(status().isOk())

                // Tarkistetaan että controller palautti addgame templaten.
                .andExpect(view().name("addgame"))

                // Tarkistetaan että modelissa on kaikki mitä addgame sivu tarvitsee.
                .andExpect(model().attributeExists("game", "developers", "genres", "platforms"));
    }

    @Test // Testataan User käyttäjänä päästä pelin lisäys sivulle.
    @WithMockUser(authorities = "USER") // Kirjaudutaan user käyttäjänä.
    void addGameAsUser() throws Exception {
        // Avataan addgame sivu user käyttäjänä.
        mockMvc.perform(get("/admin/games/addgame"))
                // Tarkistetaan että vastaus on forbidden eli koodi 403.
                .andExpect(status().isForbidden());
    }
}
