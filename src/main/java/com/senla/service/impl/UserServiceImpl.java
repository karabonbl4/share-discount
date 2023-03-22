package com.senla.service.impl;

import com.senla.dao.RoleRepository;
import com.senla.dao.UserRepository;
import com.senla.exceptions.MissingRequiredDataException;
import com.senla.exceptions.ObjectAlreadyExistException;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.model.dto.TrademarkDto;
import com.senla.model.dto.mapper.CustomUserPurchaseAccountMapper;
import com.senla.model.dto.save.UserDtoForUpdate;
import com.senla.model.dto.save.UserPurchaseServiceDto;
import com.senla.model.entity.Role;
import com.senla.model.entity.Trademark;
import com.senla.model.entity.User;
import com.senla.service.UserService;
import com.senla.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final CustomUserPurchaseAccountMapper customMapper;

    @SneakyThrows
    @Override
    public UserDto save(UserDto userDto) {
        this.checkRequiredData(userDto);
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.save(new Role("ROLE_USER"))));
        return modelMapper.map(userRepository.save(this.activateUser(user)), UserDto.class);
    }

    @SneakyThrows
    @Override
    public UserPurchaseServiceDto createPurchaseServiceAccount(UserPurchaseServiceDto purchaseAccount) {
        TrademarkDto trademark = purchaseAccount.getTrademark();
        if (trademark == null) {
            throw new MissingRequiredDataException("trademark");
        }
        this.checkRequiredData(purchaseAccount);
        String encodePassword = passwordEncoder.encode(purchaseAccount.getPassword());
        User savingPurchaseAccount = customMapper.map(purchaseAccount);
        savingPurchaseAccount.setPassword(encodePassword);
        savingPurchaseAccount.setIsActive(true);
        savingPurchaseAccount.setRoles(List.of(roleRepository.save(new Role("ROLE_SERVICE"))));
        savingPurchaseAccount.setTrademarks(List.of(modelMapper.map(trademark, Trademark.class)));
        userRepository.save(this.activateUser(savingPurchaseAccount));
        return purchaseAccount;
    }


    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<UserDto> findAll(Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber - 1, pageSize);
        return userRepository.findAll(paging).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId) {
        User user = userRepository.getReferenceById(userId);
        userRepository.delete(user);
    }

    @SneakyThrows
    @Override
    public UserDtoForUpdate update(UserDtoForUpdate userDto) {
        if (!getAuthUser().getId().equals(userDto.getId())) {
            throw new AccessDeniedException("Access denied!");
        }
        if (userDto.getUsername() == null || userDto.getPassword() == null ||
                userDto.getFirstName() == null || userDto.getSurName() == null ||
                userDto.getEmail() == null || userDto.getPhoneNumber() == null) {
            throw new MissingRequiredDataException();
        }
        User user = userRepository.findById(userDto.getId()).orElseThrow(EntityNotFoundException::new);
        user.setFirstName(userDto.getFirstName());
        user.setSurName(userDto.getSurName());
        user.setBirthday(userDto.getBirthday());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.saveAndFlush(user);
        return userDto;
    }

    @Override
    public UserDto getAuthUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userAuthenticationName = ((UserDetails) principal).getUsername();
        return modelMapper.map(userRepository.findByUsername(userAuthenticationName), UserDto.class);
    }

    @Override
    public String getAuthUserUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }

    @Override
    public void setAdminToTrademark(Long userId, Long trademarkId) {
        userRepository.setAdminTrademark(userId, trademarkId);
    }

    @Override
    public UserDto findByCardId(Long cardId) {
        User byCards_id = userRepository.findByCards_Id(cardId);
        return modelMapper.map(byCards_id, UserDto.class);
    }

    @Override
    public void removeAdminTrademark(Long adminId, Long trademarkId) {
        userRepository.removeAdminTrademark(adminId, trademarkId);
    }

    @Override
    public User recalculateScore(UserDto ownerCard, UserDto buyer, BigDecimal sumScore) {
        if (buyer.getId().equals(ownerCard.getId())) {
            BigDecimal newOwnersScore = ownerCard.getScore().add(sumScore);
            User owner = userRepository.findById(ownerCard.getId()).orElseThrow(EntityNotFoundException::new);
            owner.setScore(newOwnersScore);
            return owner;
        }
        BigDecimal newBuyerScore = buyer.getScore().add(sumScore.divide(BigDecimal.valueOf(2L)));
        BigDecimal newOwnersScore = ownerCard.getScore().add(sumScore.divide(BigDecimal.valueOf(2L)));
        User savingBauer = userRepository.findById(buyer.getId()).orElseThrow(EntityNotFoundException::new);
        savingBauer.setScore(newBuyerScore);
        userRepository.setNewScoreForUser(newOwnersScore, ownerCard.getId());
        return savingBauer;
    }

    @Override
    public UserDto findByCouponId(Long couponId) {
        User userByCoupon = userRepository.findByCoupons_Id(couponId);
        if (userByCoupon == null) {
            return null;
        }
        return modelMapper.map(userByCoupon, UserDto.class);
    }

    @Override
    public List<UserDto> findByTrademarkId(Long trademarkId) {
        return userRepository.findByTrademarks_Id(trademarkId).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private <T extends UserDto> void checkRequiredData(T t) {
        if (t.getUsername() == null || t.getPassword() == null ||
                t.getFirstName() == null || t.getSurName() == null ||
                t.getEmail() == null || t.getPhoneNumber() == null) {
            throw new MissingRequiredDataException();
        }
        if (userRepository.findByUsername(t.getUsername()) != null) {
            throw new ObjectAlreadyExistException(t.getUsername());
        }
    }

    private User activateUser(User user) {
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }
}
