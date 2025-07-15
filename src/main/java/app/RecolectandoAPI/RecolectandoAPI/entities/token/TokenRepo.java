package app.RecolectandoAPI.RecolectandoAPI.entities.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TokenRepo extends JpaRepository<Token, Long> {
    Token findByToken(String token);

    List<Token> findAllExpiredIsFalseOrRevokedIsFalseByUserId(Long id);

    @Query("select t.user.id from Token t where t = :token")
    Long findUser_IdByToken(@Param("token") Token t);
}
