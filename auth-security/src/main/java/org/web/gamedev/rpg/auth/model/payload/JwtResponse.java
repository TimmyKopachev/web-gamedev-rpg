package org.web.gamedev.rpg.auth.model.payload;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtResponse {
  private String token;
  private String refreshToken;
  private Long id;
  private String username;
  private List<String> roles;
}
