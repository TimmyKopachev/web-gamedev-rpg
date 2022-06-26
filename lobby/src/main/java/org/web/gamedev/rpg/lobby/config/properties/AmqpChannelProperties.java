package org.web.gamedev.rpg.lobby.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = "game.amqp.matchmaking")
public class AmqpChannelProperties {

    private String queue;
    private String exchange;
    private String routingKey;
}
