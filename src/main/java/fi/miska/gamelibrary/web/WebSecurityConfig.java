package fi.miska.gamelibrary.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
			.authorizeHttpRequests( authorize -> authorize        
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/**").hasAuthority("ADMIN")
                .requestMatchers("/games/**", "/", "/home", "/api**", "/allGames/**", "/css/**", "/h2-console/**").permitAll()    // require any authentication.
				.anyRequest().authenticated()                 
			)                                             
		.formLogin( formlogin -> formlogin                   
			.loginPage("/login")                          
			.defaultSuccessUrl("/games", true)     
			.permitAll()                                  
		)
		.logout( (logout) -> logout.logoutSuccessUrl("/games")
			.permitAll()
        )
        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
		.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
		
		return http.build();
	}

}
