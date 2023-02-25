package com.senla.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(){
        super("Entity not found!");
    }
}
