package org.web.gamedev.rpg.lobby.config;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.web.gamedev.rpg.lobby.aggregation.CorrelationLobbyAggregationStrategy;
import org.web.gamedev.rpg.lobby.aggregation.LobbyAggregationGroupProcessor;
import org.web.gamedev.rpg.lobby.aggregation.ReleaseLobbyAggregationStrategy;
import org.web.gamedev.rpg.lobby.config.properties.AmqpChannelProperties;
import org.web.gamedev.rpg.lobby.model.SearchingMatchmakingRequest;
import org.web.gamedev.rpg.lobby.processor.ReadyLobbyLoggingHandler;

@Slf4j
@Configuration
@EnableConfigurationProperties(
    value = {
      AmqpChannelProperties.class,
    })
@AllArgsConstructor
public class InboundAmqpConfiguration implements InitializingBean {

  private final AmqpAdmin amqpAdmin;

  private final List<AmqpChannelProperties> amqpProperties;

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("Setting up the Queue/Exchange/Binding for local environment");
    setupAmqpQueues();
  }

  public void setupAmqpQueues() {
    amqpProperties.forEach(
        amqpProperty -> {
          Queue queue = new Queue(amqpProperty.getQueue());
          TopicExchange topicExchange = new TopicExchange(amqpProperty.getExchange());
          amqpAdmin.declareQueue(queue);
          amqpAdmin.declareExchange(topicExchange);
          amqpAdmin.declareBinding(
              BindingBuilder.bind(queue).to(topicExchange).with(amqpProperty.getRoutingKey()));
        });
  }
/*

  @Bean
  CustomDeserializer<?> deserializer() {
    return new CustomDeserializer<>(SearchingMatchmakingRequest.class);
  }
*/

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(new ProtobufMessageConverter());
    factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
    return factory;
  }

  @Bean
  IntegrationFlow amqpInbound(
      ConnectionFactory connectionFactory,
      ReleaseLobbyAggregationStrategy releaseStrategy,
      CorrelationLobbyAggregationStrategy correlationLobby,
      LobbyAggregationGroupProcessor aggregationGroupProcessor) {
    return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, "matchmaking"))
        //.handle()
        .aggregate(
            aggregatorSpec ->
                aggregatorSpec
                    .releaseStrategy(releaseStrategy)
                    .correlationStrategy(correlationLobby)
                    .outputProcessor(aggregationGroupProcessor)
                    .expireGroupsUponCompletion(true))
        .handle(new ReadyLobbyLoggingHandler())
        .get();
  }
}
