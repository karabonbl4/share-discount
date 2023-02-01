package com.senla.service;

import com.senla.service.dto.TrademarkDto;


public interface TrademarkService {
    TrademarkDto save(TrademarkDto trademarkDto);
    TrademarkDto findById(Long id);
    boolean delete(TrademarkDto trademarkDto);
    TrademarkDto update(TrademarkDto trademarkDto);
}
