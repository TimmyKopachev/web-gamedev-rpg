package org.web.gamedev.rpg.lobby.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadyLobbyResponse {

  private String lobbyUUID;

  private List<String> playerUUIDs;
  private MatchmakingDetails matchmakingDetails;
}
