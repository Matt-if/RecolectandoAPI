package app.RecolectandoAPI.RecolectandoAPI.jwt;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import app.RecolectandoAPI.RecolectandoAPI.entities.user.User;
import app.RecolectandoAPI.RecolectandoAPI.entities.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserRepo userRepo;

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    @Value("${jwt.token.expiration}")
    private int TOKEN_EXPIRATION;
    @Value("${jwt.refresh-token.expiration}")
    private int REFRESH_TOKEN_EXPIRATION;

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
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

    // -------------------------------------------- nuevo ------------------------
    public String generateToken(User user) {
        return buildToken(user, TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, REFRESH_TOKEN_EXPIRATION);
    }

    private String buildToken( final User user, final long expiration) {
        return Jwts.builder()
                .id(user.getId().toString())
                .claims(Map.of("rol", user.getRole()))
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
