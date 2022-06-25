package org.web.gamedev.rpg.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Roles")
@Table(name = "Roles")
public class Role implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@NotEmpty(message = "*Please provide the role name")
    @Column(name = "name", nullable = false)
    private String name;
    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Roles_Users",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "user_id"})})
    private Set<User> users = new HashSet();*/
}
