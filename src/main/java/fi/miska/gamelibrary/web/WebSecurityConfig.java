package fi.miska.gamelibrary.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests( authorize -> authorize        // / and /home paths are configured to not
				.requestMatchers("/games/**", "/css/**", "/h2-console/**").permitAll()    // require any authentication.
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()                 // All other paths must be authenticated.
			)                                             // If you don't give loginPage
		.formLogin( formlogin -> formlogin                    // your application will use the
			.loginPage("/login")                          // spring boot default login page.
			.defaultSuccessUrl("/games", true)      // <-- Tells where to go after
			.permitAll()                                  // successful login.
		)
		.logout( (logout) -> logout.logoutSuccessUrl("/games")
			.permitAll()
        )
        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
		.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
		
		return http.build();
	}

}
