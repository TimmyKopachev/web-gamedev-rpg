package org.web.gamedev.rpg.forum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.web.gamedev.rpg.forum.service.DiscussionService;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class ApplicationForumRunner implements ApplicationRunner {

  @Autowired private DiscussionService discussionService;

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationForumRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }

  @Override
  public void run(ApplicationArguments args) {

    log.info("----ALL DISCUSSIONS----");
    discussionService.findAll().forEach(d -> log.info("discussion: {}", d));
    log.info("--------");
  }
}
