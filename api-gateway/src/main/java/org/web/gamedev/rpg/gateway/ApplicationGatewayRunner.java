package org.web.gamedev.rpg.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class ApplicationGatewayRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationGatewayRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }
}
