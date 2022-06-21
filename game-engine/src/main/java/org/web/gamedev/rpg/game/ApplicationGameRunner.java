package org.web.gamedev.rpg.game;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
public class ApplicationGameRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationGameRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }
}
