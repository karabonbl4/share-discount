package com.senla.repository.impl;

import com.senla.model.DiscountCard;
import com.senla.repository.DefaultRepositoryImpl;
import com.senla.repository.DiscountCardRepository;
import org.springframework.stereotype.Repository;


@Repository
public class DiscountCardRepositoryImpl extends DefaultRepositoryImpl<DiscountCard, Long> implements DiscountCardRepository {

}
