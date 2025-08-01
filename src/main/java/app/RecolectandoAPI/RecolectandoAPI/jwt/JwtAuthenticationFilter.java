package app.RecolectandoAPI.RecolectandoAPI.jwt;

import app.RecolectandoAPI.RecolectandoAPI.errorHandling.JwtAuthenticationEntryPoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter se usa para crear filtros personalizados.
    // Para garantizar que el filtro se ejecute una vez por cada solicitud http, aunque hayan varios filtros en la cadena.
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authEntryPoint;

    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;

        if (token==null) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getServletPath();
        if (path.startsWith("/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        //Si el token recibido es de refresh --> no se permite el acceso al recurso.
        String tokenUsage = jwtService.getTokenUsage(token);
        if ("REFRESH".equalsIgnoreCase(tokenUsage)) {
            authEntryPoint.commence(
                    request,
                    response,
                    new InsufficientAuthenticationException("No se permite usar refresh tokens para acceder a recursos.")
            );
            return;
        }

        username=jwtService.getUsernameFromToken(token);

        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
