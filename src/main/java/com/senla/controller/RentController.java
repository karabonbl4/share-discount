package com.senla.controller;

import com.senla.model.dto.RentDto;
import com.senla.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rents")
public class RentController {

    private final RentService rentService;

    @Secured(value = "ROLE_USER")
    @GetMapping(value = "/my")
    public List<RentDto> getMyCardHistoryRent(@Param("pageNumber") Integer pageNumber,
                                              @Param("pageSize") Integer pageSize,
                                              @Param("periodStart") String periodStart,
                                              @Param("periodEnd") String periodEnd,
                                              @Param("order") String order) {
        return rentService.findRentHistoryForUsersCard(pageNumber, pageSize, periodStart, periodEnd, order);
    }

    @Secured(value = "ROLE_USER")
    @GetMapping(value = "/tenant")
    public List<RentDto> getTenantHistoryRent(@Param("pageNumber") Integer pageNumber,
                                              @Param("pageSize") Integer pageSize,
                                              @Param("periodStart") String periodStart,
                                              @Param("periodEnd") String periodEnd,
                                              @Param("order") String order) {
        return rentService.findRentHistoryForTenant(pageNumber, pageSize, periodStart, periodEnd, order);
    }

    @Secured(value = "ROLE_ADMIN")
    @GetMapping(value = "/{id}")
    public RentDto getRentById(@PathVariable(value = "id") Long rentId) {
        return rentService.getById(rentId);
    }
}
