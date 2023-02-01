package com.senla.service.impl;

import com.senla.model.Trademark;
import com.senla.repository.impl.TrademarkRepository;
import com.senla.service.TrademarkService;
import com.senla.service.dto.TrademarkDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrademarkServiceImpl implements TrademarkService {
    private final ModelMapper modelMapper;
    private final TrademarkRepository trademarkRepository;

    @Override
    public TrademarkDto save(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        Trademark trademark1 = trademarkRepository.saveOrUpdate(trademark);
        return modelMapper.map(trademark1, TrademarkDto.class);
    }

    @Override
    public TrademarkDto findById(Long id) {
        Trademark trademark = trademarkRepository.findById(id);
        return modelMapper.map(trademark, TrademarkDto.class);
    }

    @Override
    public List<TrademarkDto> findAll() {
        return trademarkRepository.findAll().stream()
                .map(trademark -> modelMapper.map(trademark, TrademarkDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        trademarkRepository.delete(trademark);
        return trademarkRepository.findById(trademarkDto.getId()) == null;
    }

    @Override
    public TrademarkDto update(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        return modelMapper.map(trademarkRepository.saveOrUpdate(trademark), TrademarkDto.class);
    }
}
