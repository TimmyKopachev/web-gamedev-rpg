package org.web.gamedev.rpg.forum.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public final static String MESSAGE_REFRESH_TOKEN_EXPIRED = "Refresh token was expired. Please make a new signin request";

    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}