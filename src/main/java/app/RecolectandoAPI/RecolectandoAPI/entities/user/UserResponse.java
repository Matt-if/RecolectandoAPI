package app.RecolectandoAPI.RecolectandoAPI.entities.user;

import app.RecolectandoAPI.RecolectandoAPI.entities.dtos.DTO;

public class UserResponse implements DTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private Role role;
}
