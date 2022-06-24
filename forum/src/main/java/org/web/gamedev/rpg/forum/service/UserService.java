package org.web.gamedev.rpg.forum.service;

import org.web.gamedev.rpg.forum.model.dto.UserDto;
import org.web.gamedev.rpg.forum.model.payload.CustomUserDetails;
import org.web.gamedev.rpg.forum.model.payload.MessageResponse;
import org.web.gamedev.rpg.forum.model.payload.TokenRefreshResponse;
import org.web.gamedev.rpg.forum.model.payload.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto signUp(UserDto userDto);

    UserResponseDto signIn(UserDto userDto);

    MessageResponse signOut(Long userId);

    TokenRefreshResponse refreshToken(String refreshToken, CustomUserDetails userDetails);

    boolean isUserExist(UserDto userDto);

    UserDto getUserById(Long userId);

    Long getUserCount();

    List<UserDto> getAllUsers();

}
