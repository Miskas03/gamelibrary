package fi.miska.gamelibrary.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// Sovelluksen tietoturva asetukset, aseteteaan kuka pääsee mihinkin osoitteeseen,
// Miten kirjaudutaan ja miten salasanat hashataan.
@Configuration
public class WebSecurityConfig {

    // BCrypt olio jolla Spring Security vertaa kirjautuessa annettua salasanaa tietokannan hashiin.
    // Bean eli Spring luo olion ja käyttää sitä automaattisesti.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Asetetaan säännöt jotka tarkistetaan jokaisella pyynnöllä.
    // Spring käy säännöt läpi ylhäältä alas ja käyttää ensimmäistä osumaa,
    //eli tarkimmat säännöt ovat laitettu ensin ja yleisimmät alas.
    @Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests( authorize -> authorize
                // /admin alkavat sivut käytettävissä vain admin authorityllä.
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
                // api:n kautta tietojen muutto vain admin authorityllä.
				.requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/**").hasAuthority("ADMIN")
                // Julkisena osoitteina: pelikirjasto, api GET komento, eli voi lukea api:n kautta, CSS ja H2-konsoli.
                .requestMatchers("/games/**", "/", "/home", "/api/**", "/allGames/**", "/css/**", "/h2-console/**").permitAll()
                // Kaikki muut osoitteet vaativat kirjautumisen.
				.anyRequest().authenticated()                 
			)
            //Oma login sivu.                                         
		.formLogin( formlogin -> formlogin                   
			.loginPage("/login")
            // Kirjautumisen jälkeen ohjataan games sivulle.                          
			.defaultSuccessUrl("/games", true)
            // Login sivu on kaikille auki   
			.permitAll()                                  
		)
        //Uloskirjautumisen jälkeen ohjataan games sivulle.
		.logout( (logout) -> logout.logoutSuccessUrl("/home")
			.permitAll()
        )
        // Tehdään poikkeus H2-konsolille, CSRF tokenin tarkistuksessa.
        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
        // Sallitaan iframet jotta konsoli näkyy.
		.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
		
		return http.build();
	}

}
