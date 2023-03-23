package com.senla.service.impl;

import com.senla.service.UrlCreatorService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlCreatorServiceImpl implements UrlCreatorService {

    @Value("${app.host}")
    private String host;

    @Value("${app.port}")
    private String port;

    @SneakyThrows
    @Override
    public String generateConfirmationRequestForCard(String discountCard) {
        return host.concat(port).concat(String.format("/cards/confirmation?card=%s", discountCard));
    }

    @Override
    public String generateRequestForRentCard(String rentDiscountCard) {
        return host.concat(port).concat(String.format("/cards/rent/request?card=%s", rentDiscountCard));
    }

}
