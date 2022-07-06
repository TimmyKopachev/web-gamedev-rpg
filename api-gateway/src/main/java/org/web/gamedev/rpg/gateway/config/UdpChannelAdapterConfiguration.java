package org.web.gamedev.rpg.gateway.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.udp.MulticastReceivingChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@AllArgsConstructor
public class UdpChannelAdapterConfiguration {

  final UdpGameMessageHandler udpGameMessageHandler;

  @Bean
  public IntegrationFlow processUdpMessage() {
    return IntegrationFlows.from(new MulticastReceivingChannelAdapter("239.255.255.255", 8090))
        .handle(udpGameMessageHandler)
        .get();
  }

  @Component
  private static class UdpGameMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {

      // TODO - Aeron: MediaDriver + publisher
      String data = new String((byte[]) message.getPayload());
      log.info("[UDP]: {}", data);
    }
  }
}
