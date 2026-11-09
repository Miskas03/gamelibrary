package fi.miska.gamelibrary.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fi.miska.gamelibrary.domain.Game;
import fi.miska.gamelibrary.domain.GameRepository;


// RestController palauttaa datan JSON muodossa, eli ei Thymeleaf sivuna.
@RestController 
public class GameRestController {

    private final GameRepository gameRepository;

    //Spring antaa GameRepositorion valmiina (dependency injection).
    public GameRestController(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    // Kaikki pelit JSONina.
    @GetMapping("/allGames")
    public List<Game> gameListRest(){
        // findAll palauttaa iterable muodon joten muutetaan se listaksi
        return (List<Game>) gameRepository.findAll();
    }
    
    // Yksi peli id:n perusteella JSONina. id saadaan osoitteesta PathVariablella.
    //Palauttaa Optionalin, eli jos peliä ei löydy, vastaus on tyhjä.
    @GetMapping("/allGames/{id}")
    public Optional<Game> findGame(@PathVariable ("id") Long id){
        return gameRepository.findById(id);
    }
}
