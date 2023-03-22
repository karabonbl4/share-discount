package com.senla.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.service.Encoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Base64;


@Component
@RequiredArgsConstructor
public class EncoderImpl implements Encoder {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public <T> String encodeObject(T t) {
        byte[] bytesObject = objectMapper.writeValueAsBytes(t);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytesObject);
    }

    @SneakyThrows
    @Override
    public <T> T decodeString(String s, Class<T> tClass) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodeBytes = decoder.decode(s);
        return objectMapper.readValue(decodeBytes, tClass);
    }
}
