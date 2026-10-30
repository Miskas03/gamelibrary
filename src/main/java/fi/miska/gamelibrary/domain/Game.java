package fi.miska.gamelibrary.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity // Tämä kertoo JPA että tee tästä taulu
public class Game {
    // Näistä tulee jokaisesta oma sarake
    @Id // Tästä tulee pääavain
    @GeneratedValue(strategy = GenerationType.AUTO) // Numeroi automaattisesti id:t
    private Long id;
    private String name;
    private String info;
    private int releaseDate;

    // Yksi peli voi kuulua moneen genreen
    @ManyToMany
    private List<Genre> genres;
    // Yksi peli voi olla monella platformilla
    @ManyToMany
    private List<Platform> platforms;
    @ManyToOne
    private Developer developer;

    // Konstruktorit
    public Game() { // JPA tekee tyhjän olion ja täyttää kentät tietokannan tiedoilla
    }

    public Game(String name, Developer developer, String info, int releaseDate, List<Platform> platforms,
            List<Genre> genres) {
        this.name = name;
        this.developer = developer;
        this.info = info;
        this.releaseDate = releaseDate;
        this.platforms = platforms;
        this.genres = genres;
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

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

}
