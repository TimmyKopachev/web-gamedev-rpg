package org.web.gamedev.rpg.forum.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.web.gamedev.rpg.forum.model.entity.RoleEntity;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"password"})
public class UserDto {
    private Long id;
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("user_name")
    private String username;
    private String password;
    private Set<RoleEntity> roles;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        String fullName = "";
        if (firstName != null && !firstName.isEmpty()) {
            fullName += firstName + " ";
        }
        if (lastName != null && !lastName.isEmpty()) {
            fullName += lastName;
        }
        return fullName.trim();
    }
}