package org.web.gamedev.rpg.forum.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;
    //private final static String tokenType = "Bearer";

}