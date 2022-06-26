package org.web.gamedev.rpg.forum.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.web.gamedev.rpg.forum.mapper.UserDetailsMapper;
import org.web.gamedev.rpg.forum.model.entity.UserEntity;
import org.web.gamedev.rpg.forum.model.payload.CustomUserDetails;
import org.web.gamedev.rpg.forum.repository.UserRepository;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserDetailsMapper userDetailsMapper;
  // private final PasswordEncoder passwordEncoder;

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity =
        userRepository
            .findByUsernameWithRoles(username)
            .orElseThrow(
                () -> {
                  log.error("CustomUserDetailsService.loadUserByUsername() user:[{}] not found", username);
                  return new UsernameNotFoundException(
                      String.format("User not found: %s", username));
                });
    CustomUserDetails userDetails = userDetailsMapper.getUserDetailsFromUserEntity(userEntity);
    List<GrantedAuthority> authorities =
        userEntity.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    userDetails.setAuthorities(authorities);
    return userDetails;
  }
}
