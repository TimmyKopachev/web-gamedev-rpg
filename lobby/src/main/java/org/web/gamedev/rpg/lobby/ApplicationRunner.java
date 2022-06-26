package org.web.gamedev.rpg.lobby;

import static org.web.gamedev.rpg.lobby.model.MatchmakingMode.CAPTURE_FLAG;
import static org.web.gamedev.rpg.lobby.model.MatchmakingMode.DEATHMATCH;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapperProvider;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.web.gamedev.rpg.lobby.model.MatchmakingDetails;
import org.web.gamedev.rpg.lobby.model.SearchingMatchmakingRequest;

@Slf4j
@EnableIntegration
@IntegrationComponentScan
@AllArgsConstructor
@SpringBootApplication
@EnableScheduling
public class ApplicationRunner {

  final RabbitTemplate rabbitTemplate;

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(ApplicationRunner.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }

  @SneakyThrows
  @Scheduled(fixedDelay = 5000)
  public void sendRequestToFindMatchmaking() {
    JsonObjectMapper<?, ?> objectMapper = JsonObjectMapperProvider.newInstance();
    var message = objectMapper.toJson(createSearchMatchmakingRequest(new Random().nextInt(100)));
    log.info("sending request to find a matchmaking: {}", message);
    rabbitTemplate.convertAndSend("matchmaking.exchange", "matchmaking.routing.key", message);
  }

  private static SearchingMatchmakingRequest createSearchMatchmakingRequest(Integer id) {
    var random = new Random();

    SearchingMatchmakingRequest request = new SearchingMatchmakingRequest();
    request.setPlayerUUID(String.format("player-uuid-%d", id));
    MatchmakingDetails matchmakingDetails = new MatchmakingDetails();
    matchmakingDetails.setMode(random.nextBoolean() ? DEATHMATCH : CAPTURE_FLAG);
    matchmakingDetails.setRanked(random.nextBoolean());
    matchmakingDetails.setMapUUID("MAP-uuid-1");
    request.setMatchmakingDetails(matchmakingDetails);

    return request;
  }
}
