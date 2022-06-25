package org.web.gamedev.rpg.forum.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Deprecated
@AllArgsConstructor
@Data
public class JwtResponse {
    private String token;
    //private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private List<String> roles;


}
