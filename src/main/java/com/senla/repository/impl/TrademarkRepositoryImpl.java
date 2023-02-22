package com.senla.repository.impl;

import com.senla.model.Trademark;
import com.senla.repository.DefaultRepositoryImpl;

import com.senla.repository.TrademarkRepository;
import org.springframework.stereotype.Repository;



@Repository
public class TrademarkRepositoryImpl extends DefaultRepositoryImpl<Trademark, Long> implements TrademarkRepository {

}
