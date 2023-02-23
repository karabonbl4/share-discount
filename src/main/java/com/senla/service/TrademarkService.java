package com.senla.service;

import com.senla.model.dto.TrademarkDto;

import java.util.List;


public interface TrademarkService {
    TrademarkDto save(TrademarkDto trademarkDto);
    TrademarkDto findById(Long id);
    void delete(Long trademarkId);
    TrademarkDto update(TrademarkDto trademarkDto);
    List<TrademarkDto> findAll();
}
