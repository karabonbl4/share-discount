package com.senla.exceptions;

public class MissingRequiredDataException extends Exception{
    
    public MissingRequiredDataException(){
        super("You have not entered the required data!");
    }

    public MissingRequiredDataException(String missingType){
        super(String.format("Not entered data: %s", missingType));
    }
}
