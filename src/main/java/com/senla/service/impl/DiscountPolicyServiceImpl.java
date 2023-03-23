package com.senla.service.impl;

import com.senla.dao.DiscountPolicyRepository;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.exceptions.ObjectAlreadyExistException;
import com.senla.model.dto.RoleDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.save.DiscountPolicyForSave;
import com.senla.model.entity.DiscountPolicy;
import com.senla.service.DiscountPolicyService;
import com.senla.model.dto.DiscountPolicyDto;
import com.senla.service.TrademarkService;
import com.senla.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountPolicyServiceImpl implements DiscountPolicyService {

    private final ModelMapper modelMapper;

    private final DiscountPolicyRepository discountPolicyRepository;

    private final UserService userService;

    private final TrademarkService trademarkService;

    @SneakyThrows
    @Override
    public DiscountPolicyDto save(DiscountPolicyForSave discountPolicyDto) {
        if (discountPolicyDto.getId()!= null && discountPolicyRepository.findById(discountPolicyDto.getId()).isPresent()) {
            throw new ObjectAlreadyExistException(discountPolicyDto.getTitle());
        }
        DiscountPolicy discountPolicy = modelMapper.map(discountPolicyDto, DiscountPolicy.class);
        DiscountPolicy returnedSavePolicy = discountPolicyRepository.save(discountPolicy);
        return modelMapper.map(returnedSavePolicy, DiscountPolicyDto.class);
    }

    @Override
    public DiscountPolicyDto findById(Long id) {
        DiscountPolicy discountPolicy = this.checkRightsToPolicy(id);
        return modelMapper.map(discountPolicy, DiscountPolicyDto.class);
    }

    @Override
    public List<DiscountPolicyDto> findAll(Integer pageNumber, Integer pageSize) {
        Pageable paging = this.getPaging(pageNumber, pageSize);
        return discountPolicyRepository.findAll(paging).stream()
                .map(discountPolicy -> modelMapper.map(discountPolicy, DiscountPolicyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long discountPolicyId) {
        DiscountPolicy discountPolicy = this.checkRightsToPolicy(discountPolicyId);
        discountPolicyRepository.delete(discountPolicy);
    }

    @Override
    public DiscountPolicyDto update(DiscountPolicyDto discountPolicyDto) {
        DiscountPolicy discountPolicy = this.checkRightsToPolicy(discountPolicyDto.getId());
        DiscountPolicy returnedSavePolicy = discountPolicyRepository.saveAndFlush(discountPolicy);
        return modelMapper.map(returnedSavePolicy, DiscountPolicyDto.class);
    }

    @Override
    public List<DiscountPolicyDto> findByTrademarkId(Long trademarkId, Integer pageNumber, Integer pageSize) {
        Pageable paging = this.getPaging(pageNumber, pageSize);
        UserDto authUser = userService.getAuthUser();
        if (!trademarkService.checkAdminById(authUser.getId(), trademarkId)
                && authUser.getRoles().stream().map(RoleDto::getName).noneMatch(name -> name.equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Access denied!");
        }
        return discountPolicyRepository.findByTrademark_Id(trademarkId, paging).stream()
                .map(discountPolicy -> modelMapper.map(discountPolicy, DiscountPolicyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DiscountPolicyDto findByCardId(Long cardId) {
        DiscountPolicy policyByCard = discountPolicyRepository.findByDiscountCards_Id(cardId);
        this.checkRightsToPolicy(policyByCard.getId());
        return modelMapper.map(policyByCard, DiscountPolicyDto.class);
    }

    private Pageable getPaging(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.by("id")));
    }

    private DiscountPolicy checkRightsToPolicy(Long id) {
        DiscountPolicy discountPolicy = discountPolicyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        UserDto authUser = userService.getAuthUser();
        if (!trademarkService.checkAdminById(authUser.getId(), discountPolicy.getTrademark().getId())
                && authUser.getRoles().stream().map(RoleDto::getName).noneMatch(name -> name.equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Access denied!");
        }
        return discountPolicy;
    }
}
