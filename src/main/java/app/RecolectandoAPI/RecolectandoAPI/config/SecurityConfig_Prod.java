package app.RecolectandoAPI.RecolectandoAPI.config;

import app.RecolectandoAPI.RecolectandoAPI.entities.token.Token;
import app.RecolectandoAPI.RecolectandoAPI.entities.token.TokenRepo;
import app.RecolectandoAPI.RecolectandoAPI.errorHandling.JwtAuthenticationEntryPoint;
import app.RecolectandoAPI.RecolectandoAPI.jwt.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("prod")
public class SecurityConfig_Prod {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationEntryPoint jwtAuthEntryPoint;
    private final TokenRepo tokenRepo;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults()) // Enable CORS
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/auth/login", "/auth/refresh", "/analytics/**").permitAll()
                        .requestMatchers("/auth/register").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/buildings/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/buildings/**").hasAuthority("ADMIN")
                        .requestMatchers("/retrievals/**").hasAnyAuthority("ADMIN", "USER") // podria dividirse mas para limitar al usuario comun
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(this::logout)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                )
                .build();
    }

    // Si el usuario cierra sesion, el objetivo es que el refresh token que tenia asociado ya no sea valido.
    private void logout(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication
    ) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        final String jwt = authHeader.substring(7);
        final Token storedToken = tokenRepo.findByToken(jwt);
        if (storedToken != null) {
            revokeAllUserTokens(storedToken);
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void revokeAllUserTokens(Token t) {
        Long id = tokenRepo.findUser_IdByToken(t);

        List<Token> validUserTokens = tokenRepo.findAllExpiredIsFalseOrRevokedIsFalseByUserId(id);

        if (!validUserTokens.isEmpty()) {
            for (Token token : validUserTokens) {
                token.setRevoked(true);
                token.setExpired(true);
            }
            tokenRepo.saveAll(validUserTokens);
        }
    }

}
