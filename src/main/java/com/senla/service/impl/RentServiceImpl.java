package com.senla.service.impl;

import com.senla.dao.RentRepository;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.exceptions.MissingRequiredDataException;
import com.senla.model.dto.RentDto;
import com.senla.model.dto.mapper.CustomRentDtoMapper;
import com.senla.model.entity.Rent;
import com.senla.service.RentService;
import com.senla.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;
    private final CustomRentDtoMapper customModelMapper;

    @SneakyThrows
    @Override
    public RentDto save(RentDto rentDto) {
        if (rentDto.getTenant() == null
                || rentDto.getRentCard() == null
                || rentDto.getRentEnd().isBefore(LocalDateTime.now())
                || rentDto.getRentEnd().isBefore(rentDto.getRentStart())) {
            throw new MissingRequiredDataException();
        }
        Rent savingRent = customModelMapper.map(rentDto);
        Rent savedRent = rentRepository.save(savingRent);
        return modelMapper.map(savedRent, rentDto.getClass());
    }

    @Override
    public RentDto getById(Long id) {
        this.checkId(List.of(id));
        Rent findRent = rentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(findRent, RentDto.class);
    }

    @Override
    public List<RentDto> findRentHistoryForUsersCard(Integer pageNumber, Integer pageSize, String periodStart, String periodEnd, String order) {
        List<LocalDateTime> period = this.getPeriod(periodStart, periodEnd);
        Pageable paging = this.getPaging(pageNumber, pageSize, order);
        Long id = userService.getAuthUser().getId();
        return rentRepository.findByCard_Owner_IdAndRentStartAfterAndRentEndBefore(id, period.get(0), period.get(1), paging).stream()
                .map(rent -> modelMapper.map(rent, RentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RentDto> findRentHistoryForTenant(Integer pageNumber, Integer pageSize, String periodStart, String periodEnd, String order) {
        List<LocalDateTime> period = this.getPeriod(periodStart, periodEnd);
        Pageable paging = this.getPaging(pageNumber, pageSize, order);
        Long userId = userService.getAuthUser().getId();
        return rentRepository.findByTenant_IdAndRentStartAfterAndRentEndBefore(userId, period.get(0), period.get(1), paging).stream()
                .map(rent -> modelMapper.map(rent, RentDto.class))
                .collect(Collectors.toList());
    }

    private List<LocalDateTime> getPeriod(String start, String end) {
        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(start.concat("T00:00:00"));
            endDate = LocalDateTime.parse(end.concat("T00:00:00"));
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("Incorrect input date [format: yyyy-MM-dd]");
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidParameterException("Rent period can't begin later than its end!");
        }
        return new ArrayList<>(List.of(startDate, endDate));
    }

    private void checkId(List<Long> id) {
        if (id.stream().anyMatch(ids -> ids < 1)) {
            throw new InvalidParameterException("id must be grater than 0!");
        }
    }

    private Pageable getPaging(Integer pageNumber, Integer pageSize, String order) {
        Sort.Order orderBy = this.checkOrder(order);
        return PageRequest.of(pageNumber - 1, pageSize, Sort.by(orderBy));
    }

    private Sort.Order checkOrder(String order) {
        if (order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("ascending")) {
            return Sort.Order.asc("rentEnd");
        } else if (order.equalsIgnoreCase("desc") || order.equalsIgnoreCase("descending")) {
            return Sort.Order.desc("rentEnd");
        } else {
            throw new InvalidParameterException("Order must contain one of next parameters:[asc or ascending, desc or descending]!");
        }
    }
}
