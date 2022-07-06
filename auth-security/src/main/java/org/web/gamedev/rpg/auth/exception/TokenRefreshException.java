package org.web.gamedev.rpg.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

  public static final String MESSAGE_REFRESH_TOKEN_EXPIRED =
      "Refresh token was expired. Please make a new signin request";

  public TokenRefreshException(String token, String message) {
    super(String.format("Failed for [%s]: %s", token, message));
  }
}
