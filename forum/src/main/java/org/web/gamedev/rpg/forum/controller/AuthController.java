package org.web.gamedev.rpg.forum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web.gamedev.rpg.forum.config.JwtTokenUtil;
import org.web.gamedev.rpg.forum.model.dto.JwtRequest;
import org.web.gamedev.rpg.forum.model.dto.JwtResponse;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsService userDetailsService;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {

        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication All       ####: {}", currentAuthentication);
        log.info("Authentication Name      ####: {}", currentAuthentication.getName());
        log.info("Authentication Principal ####: {}", currentAuthentication.getPrincipal());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        log.info("JWT: {}", jwt);

        return ResponseEntity.ok(new JwtResponse(jwt));

    }
}



