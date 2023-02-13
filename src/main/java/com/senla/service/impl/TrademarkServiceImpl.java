package com.senla.service.impl;

import com.senla.dao.TrademarkRepository;
import com.senla.exceptions.NotFoundException;
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
    public void save(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        trademarkRepository.save(trademark);
    }

    @Override
    public TrademarkDto findById(Long id) throws NotFoundException{
        Trademark trademark = trademarkRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(trademark, TrademarkDto.class);
    }

    @Override
    public void delete(Long trademarkId) {
        Trademark trademark = trademarkRepository.getReferenceById(trademarkId);
        trademarkRepository.delete(trademark);
    }

    @Override
    public void update(TrademarkDto trademarkDto) {
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        trademarkRepository.saveAndFlush(trademark);
    }

    @Override
    public List<TrademarkDto> findAll() {
        return trademarkRepository.findAll().stream()
                .map(trademark -> modelMapper.map(trademark, TrademarkDto.class))
                .collect(Collectors.toList());
    }
}
