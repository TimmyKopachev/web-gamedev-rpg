package org.web.gamedev.rpg.lobby.processor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.web.gamedev.rpg.lobby.model.ReadyLobbyResponse;

@Slf4j
@AllArgsConstructor
public class ReadyLobbyLoggingHandler {

  @ServiceActivator
  void readyLobbyLogging(ReadyLobbyResponse response) {
    log.info("----------------");
    log.info("Lobby is ready with details: [{}]", response);
    log.info("----------------");
  }
}
