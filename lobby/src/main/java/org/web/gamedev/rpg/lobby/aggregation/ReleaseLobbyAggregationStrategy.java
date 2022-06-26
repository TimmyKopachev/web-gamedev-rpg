package org.web.gamedev.rpg.lobby.aggregation;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.stereotype.Component;
import org.web.gamedev.rpg.lobby.config.CustomDeserializer;
import org.web.gamedev.rpg.lobby.model.SearchingMatchmakingRequest;

@Component
@AllArgsConstructor
public class ReleaseLobbyAggregationStrategy implements ReleaseStrategy {

  final CustomDeserializer<SearchingMatchmakingRequest> deserializer;

  @SneakyThrows
  @Override
  public boolean canRelease(MessageGroup messageGroup) {
    var request = deserializer.deserialize((String) messageGroup.getOne().getPayload());
    return Optional.of(request.getMatchmakingDetails())
        .map(md -> md.getMode().getPlayerSize() == messageGroup.getMessages().size())
        .orElse(Boolean.FALSE);
  }
}
