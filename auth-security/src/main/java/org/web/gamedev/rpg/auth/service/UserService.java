package org.web.gamedev.rpg.auth.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web.gamedev.rpg.auth.config.JwtTokenUtil;
import org.web.gamedev.rpg.auth.exception.TokenRefreshException;
import org.web.gamedev.rpg.auth.exception.UserCreationException;
import org.web.gamedev.rpg.auth.mapper.UserDetailsMapper;
import org.web.gamedev.rpg.auth.mapper.UserMapper;
import org.web.gamedev.rpg.auth.model.RefreshToken;
import org.web.gamedev.rpg.auth.model.Role;
import org.web.gamedev.rpg.auth.model.User;
import org.web.gamedev.rpg.auth.model.payload.MessageResponse;
import org.web.gamedev.rpg.auth.model.payload.TokenRefreshResponse;
import org.web.gamedev.rpg.auth.model.payload.UserResponseDto;
import org.web.gamedev.rpg.model.auth.UserDto;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

  private final RefreshTokenService refreshTokenService;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final JwtTokenUtil jwtTokenUtil;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final UserDetailsMapper userDetailsMapper;
  private static final String ADMIN = "ROLE_ADMIN";
  private static final String USER = "ROLE_USER";

  @Transactional
  public UserResponseDto signUp(UserDto userDto) {
    if (isUserExist(userDto)) {
      String message = String.format("User %s is already exist", userDto.getUsername());
      log.info(message);
      throw new UserCreationException(message);
    }
    return saveUser(userDto, "admin".equalsIgnoreCase(userDto.getUsername()));
  }

  @Transactional
  private UserResponseDto saveUser(UserDto userDto, boolean isAdmin) {
    try {
      User user = userMapper.getEntityFromUserDto(userDto);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      Set<Role> roles = new HashSet<>();
      roles.add(roleRepository.findByName(USER));
      if (isAdmin) {
        roles.add(roleRepository.findByName(ADMIN));
      }
      user.setRoles(roles);
      User savedUser = userRepository.save(user);
      log.info(String.format("User has been saved id: %s", savedUser.getId()));

      return UserResponseDto.builder()
          .username(userDto.getUsername())
          .token(
              jwtTokenUtil.generateToken(userDetailsMapper.getUserDetailsFromUserEntity(savedUser)))
          .refreshToken(refreshTokenService.createRefreshToken(savedUser.getId()).getToken())
          .build();

    } catch (Exception ex) {
      log.error("UserServiceImpl.saveUser() ", ex);
      throw new UserCreationException("Error during user creation");
    }
  }

  @Transactional
  public UserResponseDto signIn(UserDto userDto) {
    Optional<User> user = userRepository.findByUsername(userDto.getUsername());
    if (user.isPresent()
        && passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
      final RefreshToken refreshTokenEntity =
          refreshTokenService.createRefreshToken(user.get().getId());
      String token =
          jwtTokenUtil.generateToken(userDetailsMapper.getUserDetailsFromUserEntity(user.get()));
      UserResponseDto responseDto = new UserResponseDto();
      responseDto.setUsername(user.get().getUsername());
      responseDto.setRefreshToken(refreshTokenEntity.getToken());
      responseDto.setToken(token);

      return responseDto;
    }

    throw new IllegalArgumentException("Invalid username or password");
  }

  public MessageResponse signOut(Long userId) {
    refreshTokenService.deleteByUserId(userId);
    return new MessageResponse(0, "User signed out");
  }

  @Transactional
  public TokenRefreshResponse refreshToken(String refreshToken) {
    return refreshTokenService
        .findByToken(refreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(
            user -> {
              final RefreshToken refreshTokenEntity =
                  refreshTokenService.createRefreshToken(user.getId());
              final String jwt =
                  jwtTokenUtil.generateToken(userDetailsMapper.getUserDetailsFromUserEntity(user));
              return new TokenRefreshResponse(jwt, refreshTokenEntity.getToken());
            })
        .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in DB!"));
  }

  public boolean isUserExist(UserDto userDto) {
    Optional<User> optionalUserEntity = userRepository.findByUsername(userDto.getUsername());
    return optionalUserEntity.isPresent();
  }

  public UserDto getUserById(Long userId) {
    return userMapper.getUserDtoFromUserEntity(userRepository.getById(userId));
  }

  public Long getUserCount() {
    return userRepository.count();
  } // Example.of(..)

  public List<UserDto> getAllUsers() {
    return userMapper.getUserDtoListFromUserEntityList(userRepository.findAll());
  }
}
