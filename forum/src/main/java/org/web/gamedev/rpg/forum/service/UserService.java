package org.web.gamedev.rpg.forum.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.web.gamedev.rpg.forum.model.UserRole;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    final UserRepository userRepository;

    final PasswordEncoder passwordEncoder;


    /*@Transactional(readOnly = true)
    public Set<UserRole> findRoleByUserLogin(String login) {
        return userRepository.findRoleByUserLogin(login);
    }*/


    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        Set<UserRole> userRoles = userRepository.findRoleByUserLogin(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        //TODO: specify authorities for each role in DB!
        //https://vk.com/video-111905078_456245825?list=6602dd021816cd3936  (1h 01 minute)
        for (UserRole userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
            log.info(userRole.getRole().getName());
        }
        //TODO:
        Optional<UserRole> user = userRoles.stream().findFirst();
        if (user.isPresent()) {
            String login = user.get().getUser().getLogin();
            String password = user.get().getUser().getPassword();
            Boolean isEnabled = user.get().getUser().getActive();
            log.info("[" + login + " " + password + " " + passwordEncoder.encode(password) + "]");
            return new User(login, password, isEnabled,
                    true, true, true, authorities);
        }
        throw new RuntimeException("Set of user roles is empty.");
    }
}
