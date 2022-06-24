package org.web.gamedev.rpg.forum.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")//, schema = "game-rpg-db"
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")//, nullable = false, unique = true
    private String email;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "phone_number")
    private String phoneNumber;
    private LocalDateTime createdAt;
    @Column(name = "password")
    private String password;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    public UserEntity(Long id) {
        this.id = id;
    }
}
