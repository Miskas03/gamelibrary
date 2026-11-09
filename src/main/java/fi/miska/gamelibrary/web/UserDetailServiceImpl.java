package fi.miska.gamelibrary.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.miska.gamelibrary.domain.User;
import fi.miska.gamelibrary.domain.UserRepository;


//Spring Security käyttää tätä luokkaa käyttäjän hakemiseen tietokannasta kirjautuessa.
//Service, Spring luo olion.
@Service
// Spring Security tunnistaa luokan tämän rajapinnan kautta.
public class UserDetailServiceImpl implements UserDetailsService  {
	private final UserRepository userRepository;

	//Spring antaa UserRepositorion valmiina (dependency injection).
	public UserDetailServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	//Spring Security kutsuu tätä kun käyttäjä painaa login nappulaa
	// Se palauttaa Springille käyttäjätunnuksen, salasanahashin ja roolin.
	// Spring tekee salasana vertailun BCryptillä.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Haetaan User olio tietokannasta käyttäjätunnuksella.
		User curruser = userRepository.findByUsername(username);

		// Jos käyttäjää ei löydy kirjautuminen epäonnistuu.
        if (curruser == null){
            throw new UsernameNotFoundException("User not found: " + username);
        }
		// Tehdään Spring Securityn oma User olio. Projektissa on jo saman niminen luokka joten tarvitaan koko paketin nimi.
		UserDetails user = new org.springframework.security.core.userdetails.User(username,curruser.getPasswordHash(),
		// Rooli esim. "ADMIN" muutetaan authorityksi.
		AuthorityUtils.createAuthorityList(curruser.getRole()));
		return user;
	}
}