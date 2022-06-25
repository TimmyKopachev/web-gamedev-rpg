package org.web.gamedev.rpg.forum.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String password;
}
