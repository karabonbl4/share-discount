package com.senla.service.impl;

import com.senla.dao.TrademarkRepository;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.model.entity.Trademark;
import com.senla.service.TrademarkService;
import com.senla.model.dto.TrademarkDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class TrademarkServiceImpl implements TrademarkService {

    private final ModelMapper modelMapper;

    private final TrademarkRepository trademarkRepository;

    @Override
    public TrademarkDto save(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        Trademark returnedTrademarkAfterSaving = trademarkRepository.save(trademark);
        return modelMapper.map(returnedTrademarkAfterSaving, TrademarkDto.class);
    }

    @Override
    public TrademarkDto findById(Long id) throws EntityNotFoundException {
        Trademark trademark = trademarkRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(trademark, TrademarkDto.class);
    }

    @Override
    public void delete(Long trademarkId) {
        Trademark trademark = trademarkRepository.getReferenceById(trademarkId);
        trademarkRepository.delete(trademark);
    }

    @Override
    public TrademarkDto update(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        Trademark returnedTrademarkAfterUpdating = trademarkRepository.saveAndFlush(trademark);
        return modelMapper.map(returnedTrademarkAfterUpdating, TrademarkDto.class);
    }

    @Override
    public List<TrademarkDto> findAll() {
        return trademarkRepository.findAll().stream()
                .map(trademark -> modelMapper.map(trademark, TrademarkDto.class))
                .collect(Collectors.toList());
    }
}
