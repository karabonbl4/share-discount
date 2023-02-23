package com.senla.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(){
        super("Entity not found!");
    }
}
