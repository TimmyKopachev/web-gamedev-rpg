package org.web.gamedev.rpg.model.auth;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private Long id;
  private String email;
  private String fullName;
  private String phoneNumber;
  private String username;
  private String password;
  private Set<RoleDto> roles;

  public UserDto(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
