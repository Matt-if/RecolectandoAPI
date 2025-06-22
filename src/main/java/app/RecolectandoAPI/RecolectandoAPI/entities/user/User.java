package app.RecolectandoAPI.RecolectandoAPI.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import app.RecolectandoAPI.RecolectandoAPI.entities.retrieval.Retrieval;
import java.util.List;
import java.util.Collection;

@Data
@Entity
public class User implements UserDetails {
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Retrieval> retrievals;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
