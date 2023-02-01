package com.senla.service;

import com.senla.service.dto.TrademarkDto;

import java.util.List;

public interface TrademarkService {
    TrademarkDto save(TrademarkDto trademarkDto);
    TrademarkDto findById(Long id);
    List<TrademarkDto> findAll();
    boolean delete(TrademarkDto trademarkDto);
    TrademarkDto update(TrademarkDto trademarkDto);
}
