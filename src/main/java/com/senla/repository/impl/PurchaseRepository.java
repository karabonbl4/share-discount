package com.senla.repository.impl;

import com.senla.model.Purchase;
import com.senla.repository.DefaultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PurchaseRepository extends DefaultRepository<Purchase> {

}
