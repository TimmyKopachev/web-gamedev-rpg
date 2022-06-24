package org.web.gamedev.rpg.forum.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequest {
    private String username;
    private String password;

}
