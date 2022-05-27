package org.web.gamedev.rpg.discovery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class ApplicationDiscoveryRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationDiscoveryRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }
}
