package com.senla.exceptions;

public class SortingNotExistException extends Exception{

    public SortingNotExistException(String sorting){
        super(String.format(String.format("This sorting is not supported, choose from: %s", sorting)));
    }
}
