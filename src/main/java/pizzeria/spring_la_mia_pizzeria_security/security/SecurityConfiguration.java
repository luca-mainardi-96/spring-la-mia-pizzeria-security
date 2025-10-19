package pizzeria.spring_la_mia_pizzeria_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
        .requestMatchers("/pizza/insert", "/pizza/edit/**").hasAuthority("ADMIN")
        .requestMatchers(HttpMethod.POST, "/pizza/**").hasAnyAuthority("ADMIN")
        .requestMatchers("/offer", "/offer/**").hasAnyAuthority("ADMIN")
        .requestMatchers("/pizza", "/pizza/**").hasAnyAuthority("ADMIN", "USER")
        .requestMatchers("/").permitAll()
        .and().formLogin().and().logout();

        return http.build();
    }

    @Bean
    DatabaseUserdetailsService userDetailsService(){
        return new DatabaseUserdetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
