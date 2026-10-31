package fi.miska.gamelibrary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.miska.gamelibrary.domain.DeveloperRepository;
import fi.miska.gamelibrary.domain.GameRepository;
import fi.miska.gamelibrary.domain.Genre;
import fi.miska.gamelibrary.domain.GenreRepository;
import fi.miska.gamelibrary.domain.PlatformRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class GameController  {
    
    private GameRepository gameRepository;
    private GenreRepository genreRepository;
    private DeveloperRepository developerRepository;
    private PlatformRepository platformRepository;



    public GameController(GameRepository gameRepository, GenreRepository genreRepository, DeveloperRepository developerRepository,
            PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.developerRepository = developerRepository;
        this.platformRepository = platformRepository;
    }

    @GetMapping("/home")
    public String home(@RequestParam (name = "name", defaultValue = "käyttäjä") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }   

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/games")
    public String games(Model model){
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("developers", developerRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("platforms", platformRepository.findAll());
        return "games";
    }

    @GetMapping("/")
    public String redirect(){
        return "redirect:/home";
    }

    
    
}
