package org.web.gamedev.rpg.auth.web;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web.gamedev.rpg.auth.config.JwtTokenUtil;
import org.web.gamedev.rpg.auth.model.payload.CustomSecurityContextHolder;
import org.web.gamedev.rpg.auth.model.payload.LoginRequest;
import org.web.gamedev.rpg.auth.model.payload.MessageResponse;
import org.web.gamedev.rpg.auth.model.payload.TokenRefreshRequest;
import org.web.gamedev.rpg.auth.model.payload.UserRegistrationRequest;
import org.web.gamedev.rpg.auth.service.RefreshTokenService;
import org.web.gamedev.rpg.auth.service.UserService;
import org.web.gamedev.rpg.model.auth.UserDto;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController {

  private final CustomSecurityContextHolder securityContextHolder;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsService userDetailsService;
  private final AuthenticationManager authenticationManager;
  private final RefreshTokenService refreshTokenService;
  private final UserService userService;

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody UserRegistrationRequest request) {
    return ResponseEntity.ok()
        .body(
            userService.signUp(
                new UserDto(
                    null,
                    request.getEmail(),
                    request.getUsername(),
                    request.getPassword(),
                    request.getPassword(),
                    request.getPassword(),
                    Set.of())));
  }

  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok()
        .body(
            userService.signIn(
                new UserDto(loginRequest.getUsername(), loginRequest.getPassword())));
  }

  @PostMapping(value = "/sign-out")
  public ResponseEntity<MessageResponse> signOut() {
    return ResponseEntity.ok()
        .body(userService.signOut(securityContextHolder.getCurrentUserId()));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
    return ResponseEntity.ok().body(userService.refreshToken(request.getRefreshToken()));
  }
}
