package app.RecolectandoAPI.RecolectandoAPI.entities.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepo extends JpaRepository<Token, Long> {
    Token findByToken(String token);

    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Long userId);

    List<Token> findAllExpiredIsFalseOrRevokedIsFalseByUserId(Long id);
}
