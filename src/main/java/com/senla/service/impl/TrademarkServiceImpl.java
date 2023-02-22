package com.senla.service.impl;

import com.senla.model.Trademark;
import com.senla.repository.TrademarkRepository;
import com.senla.service.TrademarkService;
import com.senla.service.dto.TrademarkDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TrademarkServiceImpl implements TrademarkService {
    private final ModelMapper modelMapper;
    private final TrademarkRepository trademarkRepository;

    @Override
    public void save(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        trademarkRepository.save(trademark);
    }

    @Override
    public TrademarkDto findById(Long id) {
        Trademark trademark = trademarkRepository.findById(id);
        return modelMapper.map(trademark, TrademarkDto.class);
    }

    @Override
    public boolean delete(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        trademarkRepository.delete(trademark);
        return trademarkRepository.findById(trademarkDto.getId()) == null;
    }

    @Override
    public void update(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        trademarkRepository.update(trademark);
    }
}
