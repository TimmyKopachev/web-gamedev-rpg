package org.web.gamedev.rpg.forum.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.web.gamedev.rpg.forum.model.entity.UserEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "jwt_token")
@NoArgsConstructor
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @Column(name = "refresh_token", nullable = false, unique = true)
    private String token;
    @Column(name = "issued_date",nullable = false)
    private LocalDate issuedDate;
    @Column(name = "expiration_date",nullable = false)
    private LocalDate expirationDate;
    // Instant //LocalDate //Date
}