package org.web.gamedev.rpg.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Users")
@Table(name = "Users")
public final class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Length(min = 4, message = "*Your login must have at least 4 characters")
    //@NotEmpty(message = "*Please provide your login")
    @Column(name = "login", nullable = false)
    private String login;
    //@Length(min = 4, message = "*Your password must have at least 4 characters")
    //@NotEmpty(message = "*Please provide your password")
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_active", columnDefinition = "smallint DEFAULT 1", nullable = false)
    private Boolean active;
    //private Client client;
    //@ManyToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
   // private Set<Role> roles = new HashSet();


}
