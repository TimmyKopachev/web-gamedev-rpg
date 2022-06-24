package org.web.gamedev.rpg.forum.model.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TokenRefreshRequest {
    private String refreshToken;

}