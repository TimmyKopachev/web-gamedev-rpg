package org.web.gamedev.rpg.forum.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.web.gamedev.rpg.forum.model.entity.RoleEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String password;
    private Set<RoleEntity> roles;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<String> getRoleList() {
        /*List<GrantedAuthority> authorities = userEntity.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());*/
        return roles.stream().map(RoleEntity::getName).collect(Collectors.toList());
    }
}