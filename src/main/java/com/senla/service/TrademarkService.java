package com.senla.service;

import com.senla.model.dto.TrademarkDto;
import com.senla.model.entity.Trademark;

import java.util.List;


public interface TrademarkService {
    TrademarkDto save(TrademarkDto trademarkDto);

    TrademarkDto findById(Long id);

    void delete(Long trademarkId);

    TrademarkDto update(TrademarkDto trademarkDto);

    List<TrademarkDto> findAll(Integer pageNumber, Integer pageSize);

    List<TrademarkDto> getByAdminId(Long adminId);

    boolean checkAdminById(Long adminId, Long trademarkId);

    TrademarkDto findByPolicyId(Long policyId);

}
