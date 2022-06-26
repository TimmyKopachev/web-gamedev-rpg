package org.web.gamedev.rpg.auth;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApplicationSecurityRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationSecurityRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }
}
