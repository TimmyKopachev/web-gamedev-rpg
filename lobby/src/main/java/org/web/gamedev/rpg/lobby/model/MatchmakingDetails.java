package org.web.gamedev.rpg.lobby.model;

import lombok.Data;

@Data
public class MatchmakingDetails {
  private boolean ranked;
  private String mapUUID;
  private MatchmakingMode mode;
}
