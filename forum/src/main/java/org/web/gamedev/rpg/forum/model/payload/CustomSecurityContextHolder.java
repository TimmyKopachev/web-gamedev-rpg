package org.web.gamedev.rpg.forum.model.payload;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomSecurityContextHolder {
    public static Long getCurrentUserId() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }

    public static CustomUserDetails getCurrentCustomUserDetails() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails;
    }
    public static List<String>  getCurrentRoleListFromCustomUserDetails() {
        List<String> rolesList = getCurrentCustomUserDetails().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return rolesList;
    }
    public static boolean isUserLoggedIn() {
        return !(SecurityContextHolder.getContext()
                .getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}