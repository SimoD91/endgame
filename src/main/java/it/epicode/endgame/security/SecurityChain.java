package it.epicode.endgame.security;

import it.epicode.endgame.model.Tipologia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityChain {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, "/videogiochi/get/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/videogiochi/**").hasAnyAuthority(Tipologia.ADMIN.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utenti/**").hasAnyAuthority(Tipologia.USER.name(), Tipologia.ADMIN.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utenti/tipologia/**").hasAnyAuthority(Tipologia.ADMIN.name()));
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").denyAll());


        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Arrays.asList("http://localhost:4000","http://localhost:4200","http://localhost:8000","http://localhost:8080","127.0.0.1"));
        cors.addAllowedMethod(HttpMethod.OPTIONS);
        cors.addAllowedMethod(HttpMethod.GET);
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", cors);
        return configurationSource;
    }
}