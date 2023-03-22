package com.senla.dao;

import com.senla.model.entity.Trademark;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrademarkRepository extends JpaRepository<Trademark, Long> {

    List<Trademark> getByAdmins_Id(Long adminId);

    Trademark getByDiscountPolicies_Id(Long policyId);

    Optional<Trademark> findByTitle(String title);

}
