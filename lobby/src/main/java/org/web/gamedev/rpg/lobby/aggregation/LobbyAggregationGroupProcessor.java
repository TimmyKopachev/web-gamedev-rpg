package org.web.gamedev.rpg.lobby.aggregation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.web.gamedev.rpg.lobby.config.CustomDeserializer;
import org.web.gamedev.rpg.lobby.model.ReadyLobbyResponse;
import org.web.gamedev.rpg.lobby.model.SearchingMatchmakingRequest;

@Component
@AllArgsConstructor
public class LobbyAggregationGroupProcessor implements MessageGroupProcessor {

  final CustomDeserializer<SearchingMatchmakingRequest> deserializer;

  @Override
  public Object processMessageGroup(MessageGroup messageGroup) {
    List<SearchingMatchmakingRequest> requests =
        messageGroup.getMessages().stream()
            .map(Message::getPayload)
            .map(p -> deserializer.deserialize((String) p))
            .collect(Collectors.toList());

    return Optional.of(requests.stream().findFirst())
        .get()
        .map(SearchingMatchmakingRequest::getMatchmakingDetails)
        .map(
            md ->
                ReadyLobbyResponse.builder()
                    .matchmakingDetails(md)
                    .lobbyUUID(String.valueOf(UUID.randomUUID().getLeastSignificantBits()))
                    .playerUUIDs(
                        requests.stream()
                            .map(SearchingMatchmakingRequest::getPlayerUUID)
                            .collect(Collectors.toList()))
                    .build())
        .orElseThrow(() -> new RuntimeException("Can not generate lobby!"));
  }
}
