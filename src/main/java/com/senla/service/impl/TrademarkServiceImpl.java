package com.senla.service.impl;

import com.senla.dao.TrademarkRepository;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.exceptions.ObjectAlreadyExistException;
import com.senla.model.entity.Trademark;
import com.senla.service.TrademarkService;
import com.senla.model.dto.TrademarkDto;
import com.senla.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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

    private final UserService userService;

    @SneakyThrows
    @Override
    public TrademarkDto save(TrademarkDto trademarkDto) {
        if (trademarkDto.getId() != null && trademarkRepository.findById(trademarkDto.getId()).isPresent()
                || trademarkRepository.findByTitle(trademarkDto.getTitle()).isPresent()) {
            throw new ObjectAlreadyExistException(trademarkDto.getTitle());
        }
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
        Trademark trademark = trademarkRepository.findById(trademarkId).orElseThrow(EntityNotFoundException::new);
        trademarkRepository.delete(trademark);
    }

    @Override
    public TrademarkDto update(TrademarkDto trademarkDto) {
        Long authUserId = userService.getAuthUser().getId();
        if (trademarkDto.getId() == null || !this.checkAdminById(authUserId, trademarkDto.getId())) {
            throw new AccessDeniedException("Access denied!");
        }
        Trademark trademark = modelMapper.map(trademarkDto, Trademark.class);
        Trademark returnedTrademarkAfterUpdating = trademarkRepository.saveAndFlush(trademark);
        return modelMapper.map(returnedTrademarkAfterUpdating, TrademarkDto.class);
    }

    @Override
    public List<TrademarkDto> findAll(Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber - 1, pageSize);
        return trademarkRepository.findAll(paging).stream()
                .map(trademark -> modelMapper.map(trademark, TrademarkDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrademarkDto> getByAdminId(Long adminId) {
        return trademarkRepository.getByAdmins_Id(adminId).stream()
                .map(trademark -> modelMapper.map(trademark, TrademarkDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkAdminById(Long adminId, Long trademarkId) {
        return trademarkRepository.getByAdmins_Id(adminId).stream()
                .anyMatch(trademark -> trademark.getAdmins().stream()
                        .anyMatch(user -> user.getId().equals(adminId)));

    }

    @Override
    public TrademarkDto findByPolicyId(Long policyId) {
        return modelMapper.map(trademarkRepository.getByDiscountPolicies_Id(policyId), TrademarkDto.class);
    }
}
