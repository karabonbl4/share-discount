package com.senla.exceptions;

public class CardHasNotOwnerException extends Exception{

    public CardHasNotOwnerException(String card){
        super(String.format("The card: %s has no owner! ", card));
    }
}
