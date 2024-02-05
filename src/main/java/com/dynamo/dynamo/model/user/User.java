package com.dynamo.dynamo.model.user;

import com.dynamo.dynamo.model.auth.VerificationToken;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(min = 8, max = 120)
    private String password;

    private Boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
        this.enabled = false;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = false;
    }
}
