package com.senla.service.impl;


import com.senla.dao.CouponRepository;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.exceptions.ObjectAlreadyExistException;
import com.senla.exceptions.ObjectExpirationException;
import com.senla.exceptions.SortingNotExistException;
import com.senla.model.dto.mapper.CustomCouponMapper;
import com.senla.model.dto.PurchaseDto;
import com.senla.model.dto.TrademarkDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.save.CouponForSaveDto;
import com.senla.model.entity.Coupon;
import com.senla.model.sorting.SORT_COUPON;
import com.senla.service.CouponService;
import com.senla.model.dto.CouponDto;
import com.senla.service.PurchaseService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

    private final ModelMapper modelMapper;

    private final CouponRepository couponRepository;

    private final UserService userService;

    private final TrademarkService trademarkService;

    private final PurchaseService purchaseService;

    private final CustomCouponMapper customCouponMapper;

    @SneakyThrows
    @Override
    public CouponDto save(CouponDto couponDto) {
        if (couponDto.getId() != null && couponRepository.existsById(couponDto.getId())) {
            throw new ObjectAlreadyExistException(couponDto.getName());
        }
        UserDto authUser = userService.getAuthUser();
        List<TrademarkDto> adminsTrademark = trademarkService.getByAdminId(authUser.getId());
        if (adminsTrademark.isEmpty() || adminsTrademark.stream().noneMatch(trademark -> trademark.equals(couponDto.getTrademark()))) {
            throw new AccessDeniedException("You don't have access to the trademark!");
        }
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        Coupon savedCoupon = couponRepository.save(coupon);
        return modelMapper.map(savedCoupon, CouponDto.class);
    }

    @Override
    public CouponDto findById(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        CouponDto searchedCoupon = modelMapper.map(coupon, CouponDto.class);
        checkAvailabilityCoupon(searchedCoupon);
        return searchedCoupon;
    }

    @SneakyThrows
    @Override
    public List<CouponDto> findAllWithSort(Integer pageNo, Integer pageSize, String[] sort) {
        String[] checkedSorting = this.checkSorting(sort);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(checkedSorting));
        UserDto authUser = userService.getAuthUser();
        List<TrademarkDto> usersTrademarks = trademarkService.getByAdminId(authUser.getId());
        if (authUser.getRoles().stream().noneMatch(roleDto -> roleDto.getName().equals("ROLE_ADMIN"))) {
            return usersTrademarks.isEmpty()
                    ? this.findSortedCouponsForUser(pageable)
                    : this.findSortedForAdminTrademark(authUser.getId(), pageable);
        }
        return couponRepository.findAll(pageable).stream()
                .map(coupon -> modelMapper.map(coupon, CouponDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void reserve(Long couponId) {
        CouponDto reservedCoupon = modelMapper.map(couponRepository.getReferenceById(couponId), CouponDto.class);
        checkAvailabilityCoupon(reservedCoupon);
        UserDto authUser = userService.getAuthUser();
        List<CouponDto> usersCoupons = this.findByUser(authUser.getId());
        List<TrademarkDto> usersTrademarks = trademarkService.getByAdminId(authUser.getId());
        if (!usersCoupons.isEmpty() && usersCoupons.stream().anyMatch(couponDto -> couponDto.getId().equals(reservedCoupon.getId()))) {
            throw new AccessDeniedException("You already have this coupon!");
        } else if (!usersTrademarks.isEmpty() && usersTrademarks.stream().anyMatch(trademarkDto -> trademarkDto.getTitle().equals(reservedCoupon.getTrademark().getTitle()))) {
            throw new AccessDeniedException("Coupon reservation for trademark owner is forbidden!");
        }
        CouponForSaveDto couponForSaveDto = modelMapper.map(reservedCoupon, CouponForSaveDto.class);
        couponForSaveDto.setUser(authUser);
        Coupon updatedCoupon = customCouponMapper.convertFromSaveDto(couponForSaveDto);
        couponRepository.saveAndFlush(updatedCoupon);
    }

    @Override
    public void delete(Long couponId) {
        Coupon coupon = couponRepository.getReferenceById(couponId);
        couponRepository.delete(coupon);
    }

    @Override
    public CouponDto findByPurchaseId(Long purchaseId) {
        Coupon couponByPurchase = couponRepository.getCouponByPurchase_Id(purchaseId);
        if (couponByPurchase == null) {
            throw new EntityNotFoundException();
        }
        UserDto authUser = userService.getAuthUser();
        if (authUser.getRoles().stream().noneMatch(roleDto -> roleDto.getName().equals("ROLE_ADMIN"))) {
            List<TrademarkDto> trademarks = trademarkService.getByAdminId(authUser.getId());
            if (trademarks.isEmpty() || trademarks.stream()
                    .noneMatch(trademarkDto -> trademarkDto.getTitle()
                            .equals(couponByPurchase.getTrademark().getTitle()))) {
                List<PurchaseDto> purchases = purchaseService.findByUserId(authUser.getId());
                if (purchases.isEmpty() || purchases.stream().noneMatch(purchaseDto -> purchaseDto.getId().equals(purchaseId))) {
                    throw new AccessDeniedException("Access denied!");
                }
            }
        }
        return modelMapper.map(couponByPurchase, CouponDto.class);
    }

    @Override
    public CouponDto update(CouponDto couponDto) {
        checkAccessRightsToCoupon(couponDto);
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        Coupon returnedCoupon = couponRepository.saveAndFlush(coupon);
        return modelMapper.map(returnedCoupon, CouponDto.class);
    }

    @SneakyThrows
    @Override
    public void deactivate(Long id) {
        Coupon couponFromDB = couponRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (couponFromDB.getIsUsed()) {
            throw new ObjectExpirationException(couponFromDB.getName());
        }
        CouponDto deactivatedCouponDto = modelMapper.map(couponFromDB, CouponDto.class);
        checkAccessRightsToCoupon(deactivatedCouponDto);
        couponRepository.deactivateCoupon(id);
    }

    @SneakyThrows
    private void checkAvailabilityCoupon(CouponDto checkedCoupon) {
        UserDto authUser = userService.getAuthUser();
        UserDto userByCoupon = userService.findByCouponId(checkedCoupon.getId());
        if (userByCoupon != null && !userByCoupon.getUsername().equals(authUser.getUsername())) {
            throw new AccessDeniedException("Coupon is taken already!");
        } else if (checkedCoupon.getEndDate().isBefore(ChronoLocalDate.from(LocalDateTime.now())) || checkedCoupon.getIsUsed().equals(true)) {
            throw new ObjectExpirationException(checkedCoupon.getName());
        }
    }

    @Override
    public List<CouponDto> findByUser(Integer pageNo, Integer pageSize, String[] sort) {
        Long userId = userService.getAuthUser().getId();
        String[] checkSorting = this.checkSorting(sort);
        Pageable paging = PageRequest.of(pageNo - 1, pageSize, Sort.by(checkSorting));
        return couponRepository.getByUser_Id(userId, paging).stream()
                .map(coupon -> modelMapper.map(coupon, CouponDto.class))
                .collect(Collectors.toList());
    }


    @SneakyThrows
    private String[] checkSorting(String[] sorting) {
        if (sorting.length == 0) {
            throw new SortingNotExistException(Arrays.toString(SORT_COUPON.values()));
        }
        List<String> checkedSorting = new ArrayList<>();
        for (String sort : sorting) {
            if (Arrays.stream(SORT_COUPON.values()).anyMatch(value -> value.toString().equalsIgnoreCase(sort))) {
                checkedSorting.add(SORT_COUPON.valueOf(sort.toUpperCase()).getDescription());
            } else {
                throw new SortingNotExistException(Arrays.toString(SORT_COUPON.values()));
            }
        }
        return checkedSorting.toArray(new String[0]);
    }

    private void checkAccessRightsToCoupon(CouponDto checkedCoupon) {
        UserDto authUser = userService.getAuthUser();
        List<TrademarkDto> usersTrademarks = trademarkService.getByAdminId(authUser.getId());
        if (usersTrademarks.isEmpty() || usersTrademarks.stream()
                .anyMatch(trademarkDto -> this.findByTrademarkId(trademarkDto.getId())
                        .stream().noneMatch(couponDto1 -> couponDto1.getId().equals(checkedCoupon.getId())))) {
            throw new AccessDeniedException("Access denied!");
        }
    }

    private List<CouponDto> findByUser(Long userId) {
        return couponRepository.getByUser_Id(userId, Pageable.unpaged()).stream()
                .map(coupon -> modelMapper.map(coupon, CouponDto.class))
                .collect(Collectors.toList());
    }

    private List<CouponDto> findByTrademarkId(Long trademarkId) {
        return couponRepository.getByTrademark_Id(trademarkId).stream()
                .map(coupon -> modelMapper.map(coupon, CouponDto.class))
                .collect(Collectors.toList());
    }

    private List<CouponDto> findSortedCouponsForUser(Pageable paging) {
        return couponRepository.findDistinctByIsUsedFalseAndEndDateGreaterThanEqualAndUser_IdNull(LocalDate.now(), paging).stream()
                .map(coupon -> modelMapper.map(coupon, CouponDto.class))
                .collect(Collectors.toList());
    }

    private List<CouponDto> findSortedForAdminTrademark(Long adminId, Pageable paging) {
        return couponRepository.findForAdminTrademark(adminId, LocalDate.now(), paging).stream()
                .map(coupon -> modelMapper.map(coupon, CouponDto.class))
                .collect(Collectors.toList());
    }
}
