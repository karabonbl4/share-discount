package com.senla.exceptions;

public class AlreadyExistException extends RuntimeException{

    public AlreadyExistException(String title){
        super(String.format("Entity with name(title): %s already exist!", title));
    }
}
