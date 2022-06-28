package org.web.gamedev.rpg.lobby.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class ProtobufMessageConverter implements MessageConverter {
  @Override
  public Message toMessage(Object o, MessageProperties messageProperties)
      throws MessageConversionException {
    return null;
  }

  @Override
  public Object fromMessage(Message message) throws MessageConversionException {
    return null;
  }

//  private static Map<String, MessageLite.Builder> builderMap;
//
//  static {
//    builderMap = new HashMap<>();
//    builderMap.put(Event.class.getSimpleName(), Event.newBuilder());
//    builderMap.put(SelectMessage.class.getSimpleName(), SelectMessage.newBuilder());
//    builderMap.put(CreateMessage.class.getSimpleName(), CreateMessage.newBuilder());
//    builderMap.put(ClearMessage.class.getSimpleName(), ClearMessage.newBuilder());
//    builderMap.put(BackForthMessage.class.getSimpleName(), BackForthMessage.newBuilder());
//  }
//
//  @Override
//  public Message toMessage(Object payload, MessageProperties messageProperties)
//      throws MessageConversionException {
//    String messageType = payload.getClass().getSimpleName();
//    if (!builderMap.containsKey(messageType)) {
//      throw new MessageConversionException("not support message type:" + messageType);
//    }
//    messageProperties.setHeader("messageType", messageType);
//    MessageLite messageLite = (MessageLite) object;
//    return new Message(messageLite.toByteArray(), headers);
//  }
//
//  @Override
//  public Object fromMessage(Message message) throws MessageConversionException {
//    String messageType = message.getMessageProperties().getHeaders().get("messageType").toString();
//    if (!builderMap.containsKey(messageType)) {
//      throw new MessageConversionException("not support message type:" + messageType);
//    }
//    try {
//      MessageLite.Builder builder = builderMap.get(messageType).clear();
//      builder = builder.mergeFrom(message.getBody());
//      return builder.build();
//    } catch (InvalidProtocolBufferException e) {
//      throw new MessageConversionException("deserialize message error", e);
//    }
//  }

}
