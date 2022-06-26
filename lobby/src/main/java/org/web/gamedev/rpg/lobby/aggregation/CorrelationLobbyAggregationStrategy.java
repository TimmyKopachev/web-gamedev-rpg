package org.web.gamedev.rpg.lobby.aggregation;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.web.gamedev.rpg.lobby.config.CustomDeserializer;
import org.web.gamedev.rpg.lobby.model.SearchingMatchmakingRequest;

@Component
@AllArgsConstructor
public class CorrelationLobbyAggregationStrategy implements CorrelationStrategy {

  final CustomDeserializer<SearchingMatchmakingRequest> deserializer;

  @SneakyThrows
  @Override
  public Object getCorrelationKey(Message<?> message) {
    var request = deserializer.deserialize((String) message.getPayload());
    return request.getMatchmakingDetails().hashCode();
  }
}
