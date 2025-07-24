package app.RecolectandoAPI.RecolectandoAPI.jwt;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Service
@Component // agregado el 23/7 por PROBLEMAS al levantar el contenedor
@RequiredArgsConstructor
public class JwtService {
    private final UserRepo userRepo;

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    @Value("${jwt.access-token.expiration}")
    private int ACCESS_TOKEN_EXPIRATION;
    @Value("${jwt.refresh-token.expiration}")
    private int REFRESH_TOKEN_EXPIRATION;

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public String getTokenUsage(String token) {
        return getClaim(token, claims -> claims.get("usage", String.class));
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }

    public String generateAccessToken(User user) {
        return buildToken(user, ACCESS_TOKEN_EXPIRATION, "ACCESS");
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, REFRESH_TOKEN_EXPIRATION, "REFRESH");
    }

    private String buildToken( final User user, final long expiration, String usage) {
        return Jwts.builder()
                .claims(Map.of("role", user.getRole(), "id", user.getId(), "usage", usage))
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact(); // esto crea -y serializa- el Token
    }

    private SecretKey getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY); // para decodificar la key
        return Keys.hmacShaKeyFor(keyBytes); // crea una instancia de la secret key
    }
}
