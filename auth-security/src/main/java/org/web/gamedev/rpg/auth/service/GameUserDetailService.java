package org.web.gamedev.rpg.auth.service;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.web.gamedev.rpg.auth.mapper.UserDetailsMapper;
import org.web.gamedev.rpg.auth.model.payload.CustomUserDetails;

@Slf4j
@Service
@AllArgsConstructor
public class GameUserDetailService implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserDetailsMapper userDetailsMapper;

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByUsernameWithRoles(username)
        .map(
            returnedUser -> {
              CustomUserDetails userDetails =
                  userDetailsMapper.getUserDetailsFromUserEntity(returnedUser);
              userDetails.setAuthorities(
                  returnedUser.getRoles().stream()
                      .map(role -> new SimpleGrantedAuthority(role.getName()))
                      .collect(Collectors.toList()));
              return userDetails;
            })
        .orElseThrow(
            () -> {
              log.error(
                  "CustomUserDetailsService.loadUserByUsername() user:[{}] not found", username);
              return new UsernameNotFoundException(String.format("User not found: %s", username));
            });
  }
}
