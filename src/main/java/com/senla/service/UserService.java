package com.senla.service;

import com.senla.model.dto.UserDto;
import com.senla.model.dto.save.UserDtoForUpdate;
import com.senla.model.dto.save.UserPurchaseServiceDto;
import com.senla.model.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);

    UserPurchaseServiceDto createPurchaseServiceAccount(UserPurchaseServiceDto purchaseAccount);

    UserDto findById(Long id);

    List<UserDto> findAll(Integer pageNumber, Integer pageSize);

    void delete(Long userId);

    UserDtoForUpdate update(UserDtoForUpdate userDto);

    UserDto getAuthUser();

    UserDto findByCouponId(Long couponId);

    UserDto findByCardId(Long cardId);

    List<UserDto> findByTrademarkId(Long trademarkId);

    String getAuthUserUsername();

    void setAdminToTrademark(Long userId, Long trademarkId);

    void removeAdminTrademark(Long userId, Long trademarkId);

    User recalculateScore(UserDto ownerCard, UserDto buyer, BigDecimal sumScore);
}
