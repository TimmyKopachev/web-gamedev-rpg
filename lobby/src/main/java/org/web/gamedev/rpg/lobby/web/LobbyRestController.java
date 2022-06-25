package org.web.gamedev.rpg.lobby.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web.gamedev.rpg.lobby.model.ReadyLobbyResponse;
import org.web.gamedev.rpg.lobby.model.SearchingMatchmakingRequest;

@Slf4j
@RestController
@AllArgsConstructor
public class LobbyRestController {

  @PostMapping
  @RequestMapping("/find")
  public ReadyLobbyResponse findMatchmaking(@RequestBody SearchingMatchmakingRequest request) {

    return ReadyLobbyResponse.builder().build();
  }
}
