package com.senla.dao;

import com.senla.model.entity.Rent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findByCard_Owner_IdAndRentStartAfterAndRentEndBefore(Long ownerId, LocalDateTime rentStart, LocalDateTime rentEnd, Pageable paging);

    List<Rent> findByTenant_IdAndRentStartAfterAndRentEndBefore(Long tenantId, LocalDateTime rentStart, LocalDateTime rentEnd, Pageable paging);


}
