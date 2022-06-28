package org.web.gamedev.rpg.lobby.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web.gamedev.rpg.lobby.model.SearchingMatchmakingRequest;

@Slf4j
@RestController
@AllArgsConstructor
public class LobbyRestController {

  @PostMapping
  @RequestMapping("start-game")
  public void sentRequestToFindMatchmaking(@RequestParam SearchingMatchmakingRequest request) {
    // RabbitTemplate.send("request", object,
  }


}
