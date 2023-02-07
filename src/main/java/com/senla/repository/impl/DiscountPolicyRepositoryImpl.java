package com.senla.repository.impl;

import com.senla.model.DiscountPolicy;
import com.senla.repository.DefaultRepositoryImpl;
import com.senla.repository.DiscountPolicyRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DiscountPolicyRepositoryImpl extends DefaultRepositoryImpl<DiscountPolicy, Long> implements DiscountPolicyRepository {

}
