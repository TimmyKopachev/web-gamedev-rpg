package org.web.gamedev.rpg.auth.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "jwt_token")
public class RefreshToken extends IdEntity {

  @Column(name = "refresh_token", nullable = false, unique = true)
  private String token;

  @Column(name = "issued_date", nullable = false)
  private Instant issuedDate;

  @Column(name = "expiration_date", nullable = false)
  private Instant expirationDate;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
