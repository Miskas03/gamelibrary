package fi.miska.gamelibrary.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import fi.miska.gamelibrary.domain.DeveloperRepository;
import fi.miska.gamelibrary.domain.GameRepository;
import fi.miska.gamelibrary.domain.Game;
import fi.miska.gamelibrary.domain.GenreRepository;
import fi.miska.gamelibrary.domain.PlatformRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class GameController {

    private GameRepository gameRepository;
    private GenreRepository genreRepository;
    private DeveloperRepository developerRepository;
    private PlatformRepository platformRepository;

    // Konstruktori johon Spring antaa repositoriot valmiina (dependecy injection).
    public GameController(GameRepository gameRepository, GenreRepository genreRepository,
            DeveloperRepository developerRepository,
            PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.developerRepository = developerRepository;
        this.platformRepository = platformRepository;
    }

    // Oma login sivu. Kirjautumattomat ohjataan tänne.
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Kotisivu
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // Pelilista sivu. Haetaan kaikki pelit ja viedään ne Modeliin nimeltä games
    @GetMapping("/games")
    public String games(Model model) { // haetaan kaikki pelit tietokannasta/repository ja viedään ne templateen
                                       // avaimella games.
        model.addAttribute("games", gameRepository.findAll());
        return "games";
    }

    // Yhden tietyn pelin tietosivu. Id saadaan osoitteesta PathVariablen avulla.
    @GetMapping(value = "/games/{id}")
    public String gameInfo(@PathVariable Long id, Model model) {
        //findById palauttaa Optionalin, orElseThrow antaa pelin tai virheen jos id:tä ei löydy
        Game game = gameRepository.findById(id).orElseThrow();
        model.addAttribute("game", game);
        return "gameinfo";
    }

    // Pelinlisäys lomakkeen avaus. Pääsy vain adminilla. Tuodaan pelin mahdolliset tiedot,
    // genre-, developer- jne -oliot lomakkeen valikkoihin.
    @GetMapping("/admin/games/addgame")
    public String addGame(Model model) {
        // Tyhjä Game olio lomakkeen pohjaksi johon syötetyt tiedot täytetään.
        model.addAttribute("game", new Game());
        model.addAttribute("developers", developerRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("platforms", platformRepository.findAll());
        return "addgame";
    }

    //Uuden pelin tallennus. Spring tekee lomakkeen tiedoista Game olion.
    @PostMapping("/admin/games/savegame")
    // @Valid tarkistaa Game luokan säännöt ja BindingResult sisältää mahdolliset virheet.
    public String saveGame(@Valid Game game, BindingResult bindingResult, Model model) {

        //Jos virheitä tulee, näytetään lomake uudestaan. Tästä tulee uusi pyyntö ja model tyhjenee,
        // joten listat pitää lisätä uudestaan. Game ja virheet ovat Modelissa.
        if (bindingResult.hasErrors()) {
            model.addAttribute("developers", developerRepository.findAll());
            model.addAttribute("genres", genreRepository.findAll());
            model.addAttribute("platforms", platformRepository.findAll());
            return "addgame";
        }
        gameRepository.save(game);
        //Redirect, selain tekee uuden GET pyynnön /games:iin ja haetaan pelilista.
        return "redirect:/games";
    }

    // Tietokannassa olevan pelin muokkauslomakkeen avaus. Lomake täytetään tietokannassa olevien tietojen mukaan.
    @GetMapping("/admin/games/edit/{id}")
    public String editGame(@PathVariable Long id, Model model) {
        Game game = gameRepository.findById(id).orElseThrow();
        model.addAttribute("developers", developerRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("platforms", platformRepository.findAll());
        model.addAttribute("game", game);
        return "editgame";
    }

    //Muokatun pelin tallenus. Pelin id tulee lomakkeessa olevasta piilokentästä,
    // joten save päivittää vanhan pelin tiedot eikä luo uutta.
    @PostMapping("/admin/games/edit/{id}")
    public String updateGame(@ModelAttribute @Valid Game game, BindingResult bindingResult, Model model) {

        // Virheiden tullessa lisätään listat uudelleen.
        if (bindingResult.hasErrors()) {
            model.addAttribute("developers", developerRepository.findAll());
            model.addAttribute("genres", genreRepository.findAll());
            model.addAttribute("platforms", platformRepository.findAll());
            return "editgame";
        }
        gameRepository.save(game);
        return "redirect:/games";
    }

    // Pelin poisto id:n perusteella. Käytetään POST sillä poisto muuttaa dataa
    @PostMapping("/admin/games/delete/{id}")
    public String deleteGame(@PathVariable Long id) {
        gameRepository.deleteById(id);
        return "redirect:/games";
    }

    // Tyhjä osoite ohjaa kotisivulle
    @GetMapping("/")
    public String redirect() {
        return "redirect:/home";
    }

}
