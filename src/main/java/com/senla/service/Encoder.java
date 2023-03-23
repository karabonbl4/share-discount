package com.senla.service;

public interface Encoder {
    <T> String encodeObject(T t);
    <T> T decodeString(String s, Class<T> clazz);
}
