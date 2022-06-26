package org.web.gamedev.rpg.friend;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApplicationFriendRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationFriendRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }
}
