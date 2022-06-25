package org.web.gamedev.rpg.forum.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@Data
@Entity
@Table(name = "jwt_token")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @Column(name = "refresh_token", nullable = false, unique = true)
    private String token;
    @Column(name = "issued_date",nullable = false)
    private Instant issuedDate;
    @Column(name = "expiration_date",nullable = false)
    private Instant expirationDate;
    // Instant //LocalDate //Date
}