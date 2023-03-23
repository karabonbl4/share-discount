package com.senla.exceptions;

public class ObjectExpirationException extends Exception{

    public ObjectExpirationException(String objectDescription){
        super(String.format("%s expired or not active!", objectDescription));
    }
}
