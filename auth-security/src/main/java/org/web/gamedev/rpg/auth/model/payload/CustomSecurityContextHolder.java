package org.web.gamedev.rpg.auth.model.payload;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomSecurityContextHolder {

  public Long getCurrentUserId() {
    CustomUserDetails userDetails =
        (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userDetails.getId();
  }

  public CustomUserDetails getCurrentCustomUserDetails() {
    return (CustomUserDetails)
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public List<String> getCurrentRoleListFromCustomUserDetails() {
    return getCurrentCustomUserDetails().getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }

  public boolean isUserLoggedIn() {
    return !(SecurityContextHolder.getContext().getAuthentication()
        instanceof AnonymousAuthenticationToken);
  }
}
