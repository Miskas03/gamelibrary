# Pelikirjasto

Pelikirjasto jossa voi selata tietokannassa olevia pelejä kirjautumattomana tai user käyttäjänä.
Admin käyttäjänä katselemisen lisäksi löytyy pelin lisääminen, pelin tietojen muokkaaminen ja pelin poistaminen.


## Sovelluksen käynnistys:
./mvnw spring-boot:run --> http://localhost:8080

Tietokantana toimii H2-muistitietokanta. Testidata luodaan sovelluksen käynnistyessä ja data tyhjenee sammuessa.

## Tekniikat joita käytetty:
 Spring Boot, Spring Security, Thymeleaf, JPA, Web MVC, H2, Bean Validation, REST, JUnit

## Testitunnukset
- tunnus: admin salasana: admin

- tunnus: user salasana: user


## Screenshotit sivulta eri tapauksissa

### Kirjautumissivu
![Kirjautumissivu](screenshots/LoginPage.jpg)

### Sivut kirjautumatta
![Home page kirjautumatta](screenshots/HomePageKirjautumatta.jpg)
![Games page kirjautumatta](screenshots/GamesPageKirjautumatta.jpg)
![Pelin tietosivu kirjautumatta](screenshots/GamesIdKirjautumattajpg.jpg)

### Sivut user käyttäjänä
![Games page userina](screenshots/GamesPageUser.jpg)
### Pelin tietosivu
![Pelin tietosivu userina](screenshots/GamesIdUserjpg.jpg)
### User yrittää päästä admin sivulle
![User yrittää päästä admin sivulle](screenshots/UserYrittaaAddgame.jpg)

### Sivut admin käyttäjänä
![Games page adminina](screenshots/GamesPageAdmin.jpg)
![Pelin tietosivu adminina](screenshots/GamesIdAdmin.jpg)
![Pelin lisäyssivu adminina](screenshots/AdminGamesAddgame.jpg)
#### Liian lyhyt info annettu
![Info liian lyhyt](screenshots/infovalidointi.jpg)
![Pelin muokkaussivu adminina](screenshots/AdminGamesEdit.jpg)


### REST
#### /api/games
![/api/games](screenshots/api_games.jpg)
#### /allGames
![/allGames](screenshots/allGames.jpg)

### Testit
![Testit](screenshots/tests.jpg)