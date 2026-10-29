package fi.miska.gamelibrary.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class GameController {

    @GetMapping("/home")
    public String home(@RequestParam (name = "name", defaultValue = "käyttäjä") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }   

    @RequestMapping("/about")
    public String about() {
        return "about";
    }
    
    
}
