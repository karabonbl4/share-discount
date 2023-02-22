package com.senla.service;

import com.senla.service.dto.TrademarkDto;


public interface TrademarkService {
    void save(TrademarkDto trademarkDto);
    TrademarkDto findById(Long id);
    boolean delete(TrademarkDto trademarkDto);
    void update(TrademarkDto trademarkDto);
}
