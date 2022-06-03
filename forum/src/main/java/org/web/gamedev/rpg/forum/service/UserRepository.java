package org.web.gamedev.rpg.forum.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.web.gamedev.rpg.forum.bean.Role;
import org.web.gamedev.rpg.forum.bean.User;
import org.web.gamedev.rpg.forum.bean.UserRole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class UserRepository {
    //@Query(nativeQuery = true, value = "select * from Roles_Users rsus, Roles r,Users u where rsus.role_id = r.id and rsus.user_id = u.id and u.login = :login")
    //Set<UserRole> findRoleByUserLogin(@Param("login") String login);

    final PasswordEncoder passwordEncoder;

    public Set<UserRole> findRoleByUserLogin(String login){
        Set<UserRole> roles = new HashSet<>();
        User user1 = new User(1L,"login1",passwordEncoder.encode("password"),true);
        User user2 = new User(2L,"login2",passwordEncoder.encode("password"),true);
        User user3 = new User(3L,"login3",passwordEncoder.encode("password"),true);

        Role role1 =new Role(1L,"USER");
        Role role2 =new Role(2L,"MANAGER");
        Role role3 =new Role(3L,"ADMIN");

        roles.add(new UserRole(1L,user1,role1));
        roles.add(new UserRole(2L,user2,role2));
        roles.add(new UserRole(3L,user3,role3));

        User user4 = new User(4L,"login",passwordEncoder.encode("password"),true);
        roles.add(new UserRole(4L,user4,role1));
        roles.add(new UserRole(5L,user4,role2));

        return roles.stream().filter(e -> e.getUser().getLogin().equalsIgnoreCase(login)).collect(Collectors.toSet());
    }
}

