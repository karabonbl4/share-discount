package com.senla.service;

import com.senla.model.dto.RentDto;

import java.util.List;

public interface RentService {
    RentDto save(RentDto rentDto);

    RentDto getById(Long id);

    List<RentDto> findRentHistoryForUsersCard(Integer pageNumber, Integer pageSize, String periodStart, String periodEnd, String order);

    List<RentDto> findRentHistoryForTenant(Integer pageNumber, Integer pageSize, String periodStart, String periodEnd, String order);
}
