package org.web.gamedev.rpg.forum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.web.gamedev.rpg.forum.service.DiscussionService;
import org.web.gamedev.rpg.forum.service.RefreshTokenService;
import org.web.gamedev.rpg.forum.service.UserServiceImpl;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class ApplicationForumRunner implements ApplicationRunner {

  @Autowired private DiscussionService discussionService;
  @Autowired private UserServiceImpl userService;
  @Autowired private RefreshTokenService refreshTokenService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationForumRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }

  @Override
  public void run(ApplicationArguments args) {
    System.out.println("Password: " + passwordEncoder.encode("password"));
    log.info("----ALL DISCUSSIONS----");
    discussionService.findAll().forEach(d -> log.info("discussion: {}", d));
    log.info("--------");
    log.info("----ALL USERS----");
    userService.getAllUsers().forEach(d -> log.info("users: {}", d));
  /*  log.info("----FIND TOKEN WITH USER ID = 1 ----");
    System.out.println(refreshTokenService.findByUserId(1L));*/

  }
}
