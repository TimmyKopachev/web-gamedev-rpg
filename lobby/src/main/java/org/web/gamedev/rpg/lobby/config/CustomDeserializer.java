package org.web.gamedev.rpg.lobby.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class CustomDeserializer<T> {

    private final Class<T> clazz;

    public CustomDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    private final ObjectMapper objectMapper =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @SneakyThrows
    public T deserialize(String data) {
        return objectMapper.readValue(data, clazz);
    }
}
