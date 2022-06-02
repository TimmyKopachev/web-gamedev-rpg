package org.web.gamedev.rpg.gateway;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.udp.MulticastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;

@Slf4j
@EnableIntegration
@SpringBootApplication
public class ApplicationGatewayRunner implements ApplicationRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationGatewayRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }

  @Override
  @SneakyThrows
  public void run(ApplicationArguments args) {
    MulticastSendingMessageHandler udpSendingMessageHandler =
        new MulticastSendingMessageHandler("239.255.255.255", 8090);

    TcpSendingMessageHandler tcpSendingMessageHandler = new TcpSendingMessageHandler();

    String payload = "Hello world";

    while (Boolean.TRUE) {
      udpSendingMessageHandler.handleMessage(MessageBuilder.withPayload(payload).build());
      log.info("---------------------------------------");
      tcpSendingMessageHandler.start();
      Thread.sleep(1000);
    }
  }
}
