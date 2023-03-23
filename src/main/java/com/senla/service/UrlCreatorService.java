package com.senla.service;

public interface UrlCreatorService {

    String generateConfirmationRequestForCard(String discountCard);

    String generateRequestForRentCard(String rentDiscountCard);
}
