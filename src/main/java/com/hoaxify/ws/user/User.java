package com.hoaxify.ws.user;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

//bu lombok eklentisi getset tostring gibi methodları otomatik yapmaktadır.
@Data
@Entity
@Table(name = "AppUser")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "{hoaxify.constraints.username.NotNull.message}")
    @UniqueUsername
    @Size(min = 4,max = 16)
    private String username;

    @NotNull(message = "{hoaxify.constraints.displayName.NotNull.message}")
    private String displayName;

    @NotNull(message = "{hoaxify.constraints.password.NotNull.message}")
    @Size(min=4)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$" , message = "{hoaxify.constraints.password.Pattern.message}")
    private String password;
    
}
