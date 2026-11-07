package fi.miska.gamelibrary.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Developer {

    // Näistä tulee jokaisesta oma sarake
    @Id // Tästä tulee pääavain
    @GeneratedValue(strategy = GenerationType.AUTO) // Tietokanta numeroi id:t tallentaessa
    private Long id;
    private String name;
    private String description;

    // Developerilla voi olla monta peliä
    @OneToMany (mappedBy = "developer")
    @JsonIgnore
    private List<Game> games;

    public Developer() {
    }

    public Developer(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

}
