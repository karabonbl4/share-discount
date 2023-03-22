package com.senla.exceptions;

public class ObjectBusyException extends Exception{

    public ObjectBusyException(){
        super("Required object is busy now!");
    }
}
