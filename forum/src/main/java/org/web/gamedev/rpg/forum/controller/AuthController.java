package org.web.gamedev.rpg.forum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web.gamedev.rpg.forum.config.JwtTokenUtil;
import org.web.gamedev.rpg.forum.error.TokenRefreshException;
import org.web.gamedev.rpg.forum.model.dto.UserDto;
import org.web.gamedev.rpg.forum.model.entity.RefreshTokenEntity;
import org.web.gamedev.rpg.forum.model.payload.*;
import org.web.gamedev.rpg.forum.service.RefreshTokenService;
import org.web.gamedev.rpg.forum.service.UserService;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final UserService userService;


    /*@PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication All       ####: {}", currentAuthentication);
        log.info("Authentication Name      ####: {}", currentAuthentication.getName());
        log.info("Authentication Principal ####: {}", currentAuthentication.getPrincipal());

        final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        final RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(jwt, refreshTokenEntity.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getRoleList()));
    }*/

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserRegistrationRequest request) {
        return ResponseEntity.ok().body(userService.signUp(new UserDto(null, request.getEmail(), request.getFirstName(), request.getLastName(), request.getPhoneNumber(), request.getUsername(), request.getPassword())));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) {
        //Authentication authentication = authenticationManager
        //.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        //SecurityContextHolder.getContext().setAuthentication(authentication);
        //CustomUserDetails userDetails = CustomSecurityContextHolder.getCurrentCustomUserDetails();
        //new JwtResponse(jwt, refreshTokenEntity.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getRoleList())
        //final String jwt = jwtTokenUtil.generateToken(userDetails);
        //final RefreshTokenEntity refreshTokenEntity = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok().body(userService.signIn(new UserDto(loginRequest.getUsername(), loginRequest.getPassword())));
    }


    @PostMapping(value = "/sign-out")
    public ResponseEntity<MessageResponse> signOut() {
        return ResponseEntity.ok().body(userService.signOut(CustomSecurityContextHolder.getCurrentUserId()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        CustomUserDetails userDetails = CustomSecurityContextHolder.getCurrentCustomUserDetails();
        return ResponseEntity.ok().body(userService.refreshToken(request.getRefreshToken(), userDetails));

    }


}



