package org.web.gamedev.rpg.forum.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
@ToString(exclude = "users")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<UserEntity> users;
}
