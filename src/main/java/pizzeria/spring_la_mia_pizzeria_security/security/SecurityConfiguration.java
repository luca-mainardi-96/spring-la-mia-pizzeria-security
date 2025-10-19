package pizzeria.spring_la_mia_pizzeria_security.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers("/user").hasAuthority("USER").requestMatchers("/admin").hasAnyAuthority("ADMIN").requestMatchers("/").permitAll().and().formLogin().and().logout();
        return http.build();
    }
}
