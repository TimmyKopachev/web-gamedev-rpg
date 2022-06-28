package org.web.gamedev.rpg.auth.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenRefreshResponse {
  private String accessToken;
  private String refreshToken;
}
