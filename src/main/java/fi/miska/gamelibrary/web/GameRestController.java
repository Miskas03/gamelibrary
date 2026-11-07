package fi.miska.gamelibrary.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fi.miska.gamelibrary.domain.Game;
import fi.miska.gamelibrary.domain.GameRepository;

@RestController 
public class GameRestController {

    private final GameRepository gameRepository;

    public GameRestController(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }


    @GetMapping("/allGames")
    public List<Game> gameListRest(){
        return (List<Game>) gameRepository.findAll();
    }

    @GetMapping("/allGames/{id}")
    public Optional<Game> findGame(@PathVariable ("id") Long id){
        return gameRepository.findById(id);
    }
}
