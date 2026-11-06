package fi.miska.gamelibrary.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity // Tämä kertoo JPA että tee tästä taulu
public class Game {
    // Näistä tulee jokaisesta oma sarake
    @Id // Tästä tulee pääavain
    @GeneratedValue(strategy = GenerationType.AUTO) // Numeroi automaattisesti id:t
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank
    @Size(max = 255, message = "Info can be at most 255 characters")
    private String info;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    // Yksi peli voi kuulua moneen genreen
    @ManyToMany
    private List<Genre> genres;
    // Yksi peli voi olla monella platformilla

    @ManyToMany
    private List<Platform> platforms;

    @NotNull(message = "Set developer or unknown")
    @ManyToOne
    private Developer developer;

    // Konstruktorit
    public Game() { // JPA tekee tyhjän olion ja täyttää kentät tietokannan tiedoilla
    }

    // Valmiin testidatan luonti, id puuttuu sillä tietokanta antaa sen tallentaessa
    public Game(String name, Developer developer, String info, LocalDate releaseDate, List<Platform> platforms,
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
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
