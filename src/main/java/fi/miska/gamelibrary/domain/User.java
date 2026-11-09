package fi.miska.gamelibrary.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


// Käyttäjä entiteetti kirjautumista varten. taulun nimeksi users sillä User on sql:ssä varattu termi.
@Entity(name = "users")
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id", nullable = false, updatable = false)
    private Long id;

    //unique: kahdella käyttäjällä ei voi olla samaa käyttäjätunnusta.
    @Column (name = "username", nullable = false, unique = true)
    private String username;

    // Tallennetaan BCrypt-hash, ei koskaan selväkielistä salasanaa.
    @Column (name = "password", nullable = false)
    private String passwordHash;

    //Rooli ilman ROLE_ alkuliitettä, joten securityssä käytetään hasAuthority.
    @Column (name = "role", nullable = false)
    private String role;

    public User() {
    }

    public User(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
