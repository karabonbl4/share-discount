package com.senla.service;

import com.senla.model.dto.TrademarkDto;

import java.util.List;


public interface TrademarkService {
    void save(TrademarkDto trademarkDto);
    TrademarkDto findById(Long id);
    void delete(Long trademarkId);
    void update(TrademarkDto trademarkDto);
    List<TrademarkDto> findAll();
}
