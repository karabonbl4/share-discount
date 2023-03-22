package com.senla.exceptions;

public class ObjectAlreadyExistException extends Exception{

    public ObjectAlreadyExistException(String title){
        super(String.format("Entity with name(title): %s already exist!", title));
    }
}
