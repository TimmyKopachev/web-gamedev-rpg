package org.web.gamedev.rpg.forum;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ApplicationForumRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationForumRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }
}
