package org.web.gamedev.rpg.forum.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    private String username;
    private String token;
    private String refreshToken;
}
