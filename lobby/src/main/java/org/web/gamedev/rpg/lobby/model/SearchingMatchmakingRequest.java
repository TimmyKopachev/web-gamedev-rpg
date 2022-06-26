package org.web.gamedev.rpg.lobby.model;

import lombok.Data;

@Data
public class SearchingMatchmakingRequest {

  private String playerUUID;
  private MatchmakingDetails matchmakingDetails;
}
