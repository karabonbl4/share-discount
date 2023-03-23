package com.senla.service.impl;

import com.senla.dao.DiscountCardRepository;
import com.senla.exceptions.CardHasNotOwnerException;
import com.senla.exceptions.DataNeedToConfirmException;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.exceptions.MissingRequiredDataException;
import com.senla.exceptions.ObjectAlreadyExistException;
import com.senla.exceptions.ObjectBusyException;
import com.senla.exceptions.SortingNotExistException;
import com.senla.model.dto.DiscountPolicyDto;
import com.senla.model.dto.RentDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.mapper.CustomDiscountCardMapper;
import com.senla.model.dto.rent.DiscountCardForRentDto;
import com.senla.model.dto.save.DiscountCardWithPolicy;
import com.senla.model.dto.save.DiscountCardWithTrademarkDto;
import com.senla.model.entity.DiscountCard;
import com.senla.model.sorting.SORT_CARD;
import com.senla.service.DiscountCardService;
import com.senla.model.dto.DiscountCardDto;
import com.senla.service.DiscountPolicyService;
import com.senla.service.Encoder;
import com.senla.service.MailService;
import com.senla.service.RentService;
import com.senla.service.TrademarkService;
import com.senla.service.UrlCreatorService;
import com.senla.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.*;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountCardServiceImpl implements DiscountCardService {

    private final ModelMapper modelMapper;

    private final DiscountCardRepository discountCardRepository;

    private final RentService rentService;

    private final CustomDiscountCardMapper customDiscountCardMapper;

    private final MailService mailService;

    private final UserService userService;

    private final TrademarkService trademarkService;

    private final Encoder encoder;

    private final UrlCreatorService urlCreatorService;

    private final DiscountPolicyService policyService;

    @Value("${app.range.discount.in.percent}")
    private BigDecimal discountRange;

    @SneakyThrows
    @Override
    public DiscountCardDto save(DiscountCardWithTrademarkDto savedCardDto) {
        Long cardNumber = savedCardDto.getNumber();
        Long trademarkId = savedCardDto.getTrademark().getId();
        if (cardNumber == null || trademarkId == null) {
            throw new MissingRequiredDataException();
        }
        UserDto authUser = userService.getAuthUser();
        boolean authUserIsAdmin = trademarkService.checkAdminById(authUser.getId(), trademarkId);
        DiscountCardWithTrademarkDto checkedForSaveDiscountCard = this.findByNumberAndTrademarkId(cardNumber, trademarkId);
        if (!authUserIsAdmin) {
            if (checkedForSaveDiscountCard != null) {
                if (checkedForSaveDiscountCard.getOwnerUser() == null && !checkedForSaveDiscountCard.getIsConfirm()) {
                    checkedForSaveDiscountCard.setOwnerUser(authUser);
                    checkedForSaveDiscountCard.setIsConfirm(true);
                    DiscountCard savedDiscountCard = discountCardRepository.saveAndFlush(customDiscountCardMapper.map(checkedForSaveDiscountCard));
                    return modelMapper.map(savedDiscountCard, DiscountCardDto.class);
                } else throw new AccessDeniedException("Discount card is already taken!");
            } else throw new DataNeedToConfirmException();
        }
        if (savedCardDto.getPolicy() == null) {
            throw new MissingRequiredDataException();
        }
        if (checkedForSaveDiscountCard != null) {
            throw new ObjectAlreadyExistException(checkedForSaveDiscountCard.getName());
        }
        savedCardDto.setIsConfirm(false);
        savedCardDto.setOwnerUser(null);
        DiscountCard discountCard = customDiscountCardMapper.map(savedCardDto);
        DiscountCard savedCard = discountCardRepository.save(discountCard);
        return modelMapper.map(savedCard, DiscountCardDto.class);
    }

    @Override
    public DiscountCardDto getById(Long id) {
        this.checkCorrectnessId(List.of(id));
        DiscountCard discountCard = discountCardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(discountCard, DiscountCardDto.class);
    }

    @Override
    public List<DiscountCardDto> findAllWithSort(Integer pageNumber, Integer pageSize, String[] sortingBy) {
        Pageable paging = this.getPaging(pageNumber, pageSize, sortingBy);
        UserDto authUser = userService.getAuthUser();
        if (authUser.getRoles().stream().noneMatch(roleDto -> roleDto.getName().equals("ROLE_ADMIN"))) {
            BigDecimal averageUserDiscount = this.getAverageUserDiscount(authUser.getId());
            BigDecimal discountMax = averageUserDiscount.add(discountRange.divide(valueOf(100L)));
            return this.findSortedCardsForUser(averageUserDiscount, discountMax, paging);
        }
        return discountCardRepository.findAllWithSortForAdmin(paging).stream()
                .map(discountCard -> modelMapper.map(discountCard, DiscountCardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        this.checkCorrectnessId(List.of(id));
        DiscountCard deletedCard = discountCardRepository.getReferenceById(id);
        discountCardRepository.delete(deletedCard);
    }

    @Override
    public DiscountCardDto update(DiscountCardWithTrademarkDto discountCardDto) {
        DiscountCard discountCard = customDiscountCardMapper.map(discountCardDto);
        DiscountCard returnedCard = discountCardRepository.saveAndFlush(discountCard);
        return modelMapper.map(returnedCard, DiscountCardDto.class);
    }

    @Override
    public List<DiscountCardDto> getCardsByUserId(Long userId, Integer pageNumber, Integer pageSize, String[] sortingBy) {
        this.checkCorrectnessId(List.of(userId));
        Pageable paging = this.getPaging(pageNumber, pageSize, sortingBy);
        return discountCardRepository.findByOwner_Id(userId, paging).stream()
                .map(discountCard -> modelMapper.map(discountCard, DiscountCardDto.class))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public DiscountCardDto confirmDiscountCardToSave(DiscountCardWithTrademarkDto discountCardDto) {
        Long authUserId = userService.getAuthUser().getId();
        if (!trademarkService.checkAdminById(authUserId, discountCardDto.getTrademark().getId())) {
            throw new AccessDeniedException("Access denied");
        }
        Long trademarkId = discountCardDto.getTrademark().getId();
        List<DiscountCard> trademarkDiscountCards = discountCardRepository.findByDiscountPolicy_Trademark_Id(trademarkId, Pageable.unpaged());
        if (trademarkDiscountCards.stream().anyMatch(discountCard -> discountCard.getNumber().equals(discountCardDto.getNumber()))) {
            throw new ObjectAlreadyExistException(discountCardDto.getName());
        }
        discountCardDto.setIsConfirm(true);
        DiscountCard confirmCard = customDiscountCardMapper.map(discountCardDto);
        DiscountCard discountCard = discountCardRepository.saveAndFlush(confirmCard);
        return modelMapper.map(discountCard, DiscountCardDto.class);
    }

    @SneakyThrows
    @Override
    public DiscountCardForRentDto confirmRentCard(DiscountCardForRentDto rentCard) {
        String authUserUsername = userService.getAuthUserUsername();
        if (!rentCard.getOwner().getUsername().equals(authUserUsername)) {
            throw new AccessDeniedException("Access denied!");
        }
        if (rentCard.getRent().getTenant() == null) {
            throw new MissingRequiredDataException(rentCard.getRent().getTenant().getClass().getName());
        }
        rentService.save(rentCard.getRent());
        discountCardRepository.activateRentOfCard(rentCard.getId());
        return rentCard;
    }

    @Override
    public List<DiscountCardDto> getMyCards(Integer pageNumber, Integer pageSize, String[] sortingBy) {
        Pageable paging = this.getPaging(pageNumber, pageSize, sortingBy);
        Long userId = userService.getAuthUser().getId();
        return discountCardRepository.findByOwner_Id(userId, paging).stream()
                .map(card -> modelMapper.map(card, DiscountCardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscountCardDto> getCardsForAdminTrademark(Long trademarkId, Integer pageNumber, Integer pageSize, String[] sortingBy) {
        this.checkCorrectnessId(List.of(trademarkId));
        Pageable paging = this.getPaging(pageNumber, pageSize, sortingBy);
        Long authUserId = userService.getAuthUser().getId();
        if (userService.findByTrademarkId(trademarkId).stream().noneMatch(userDto -> userDto.getId().equals(authUserId))) {
            throw new AccessDeniedException("Access denied to this trademark!");
        }
        return discountCardRepository.findByDiscountPolicy_Trademark_Id(trademarkId, paging).stream()
                .map(discountCard -> modelMapper.map(discountCard, DiscountCardDto.class))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public String sendRequestForRentCard(Long cardId, String startRent, String endRent) {
        this.checkCorrectnessId(List.of(cardId));
        DiscountCard rentCard = discountCardRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);
        DiscountCardForRentDto rentCardDto = modelMapper.map(rentCard, DiscountCardForRentDto.class);
        this.checkCardBeforeSendRequest(rentCardDto);
        List<LocalDateTime> period = this.getPeriod(startRent, endRent);
        UserDto tenant = userService.getAuthUser();
        RentDto rentDto = RentDto.builder()
                .tenant(tenant)
                .rentCard(modelMapper.map(rentCard, DiscountCardDto.class))
                .rentStart(period.get(0))
                .rentEnd(period.get(1))
                .build();
        rentCardDto.setRent(rentDto);
        String encodeRentCard = encoder.encodeObject(rentCardDto);
        String link = urlCreatorService.generateRequestForRentCard(encodeRentCard);
        mailService.sendEmail(rentCardDto.getOwner().getEmail(), "Request of rent card!", link);
        return "Request for rent is sent!";
    }

    @Override
    public <T> T getCardFromLink(String link, Class<T> clazz) {
        return encoder.decodeString(link, clazz);
    }

    @Override
    public String sendRequestForSaveCard(DiscountCardWithTrademarkDto confirmedCard) {
        UserDto authUser = userService.getAuthUser();
        Long trademarkId = confirmedCard.getTrademark().getId();
        confirmedCard.setOwnerUser(authUser);
        String encodeCard = encoder.encodeObject(confirmedCard);
        String confirmationRequest = urlCreatorService.generateConfirmationRequestForCard(encodeCard);
        UserDto adminTrademark = userService.findByTrademarkId(trademarkId).stream().findFirst().orElseThrow(EntityNotFoundException::new);
        mailService.sendEmail(adminTrademark.getEmail(), "Confirmation request!", confirmationRequest);
        return "Confirmation request is sent!";
    }

    @Override
    public DiscountCardWithPolicy getCardDtoWithPolicy(Long id) {
        DiscountCard byIdWithGraph = discountCardRepository.getById(id);
        return modelMapper.map(byIdWithGraph, DiscountCardWithPolicy.class);
    }

    @Override
    public DiscountCard recalculateDiscountAfterPurchase(Long cardId, BigDecimal sumPurchase, BigDecimal sumAllPurchases) {
        DiscountPolicyDto cardPolicy = policyService.findByCardId(cardId);
        BigDecimal plusDiscount = sumPurchase.divide(cardPolicy.getDiscountStep(), RoundingMode.FLOOR);
        DiscountCard discountCard = discountCardRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);
        BigDecimal actualCardDiscount = discountCard.getDiscount().add(plusDiscount.divide(valueOf(100L)));
        BigDecimal settingDiscount = actualCardDiscount.compareTo(cardPolicy.getMaxDiscount()) > 0
                ? cardPolicy.getMaxDiscount()
                : actualCardDiscount;
        discountCard.setDiscount(settingDiscount);
        return discountCard;
    }

    @SneakyThrows
    private void checkCardBeforeSendRequest(DiscountCardForRentDto discountCardDto) {
        if (discountCardDto.getIsRent()) {
            throw new ObjectBusyException();
        }
        if (discountCardDto.getOwner() == null) {
            throw new CardHasNotOwnerException(discountCardDto.getNumber().toString());
        }
        if (discountCardDto.getOwner().getId().equals(userService.getAuthUser().getId())) {
            throw new AccessDeniedException("Access denied!");
        }
    }

    private DiscountCardWithTrademarkDto findByNumberAndTrademarkId(Long number, Long trademarkId) {
        this.checkCorrectnessId(List.of(number, trademarkId));
        DiscountCard checkedCard = discountCardRepository.findByNumberAndDiscountPolicy_Trademark_Id(number, trademarkId);
        if (checkedCard == null) {
            return null;
        }
        return customDiscountCardMapper.map(checkedCard);
    }

    private Pageable getPaging(Integer pageNumber, Integer pageSize, String[] sortingBy) {
        String[] checkedSorting = this.checkSorting(sortingBy);
        return PageRequest.of(pageNumber - 1, pageSize, Sort.by(checkedSorting));
    }

    private void checkCorrectnessId(List<Long> id) {
        if (id.stream().anyMatch(ids -> ids < 1)) {
            throw new InvalidParameterException("id must be grater than 0!");
        }
    }

    @SneakyThrows
    private String[] checkSorting(String[] sorting) {
        if (sorting.length == 0) {
            throw new SortingNotExistException(Arrays.toString(SORT_CARD.values()));
        }
        List<String> checkedSorting = new ArrayList<>();
        for (String sort : sorting) {
            if (Arrays.stream(SORT_CARD.values()).anyMatch(value -> value.toString().equalsIgnoreCase(sort))) {
                checkedSorting.add(SORT_CARD.valueOf(sort.toUpperCase()).getDescription());
            } else {
                throw new SortingNotExistException(Arrays.toString(SORT_CARD.values()));
            }
        }
        return checkedSorting.toArray(new String[0]);
    }

    private List<LocalDateTime> getPeriod(String start, String end) {
        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(start);
            endDate = LocalDateTime.parse(end);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("Incorrect input date [format: yyyy-MM-ddThh:mm:ss]");
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidParameterException("Rent period can't begin later than its end!");
        }
        return new ArrayList<>(List.of(startDate, endDate));
    }

    private List<DiscountCardDto> findSortedCardsForUser(BigDecimal discountMin, BigDecimal discountMax, Pageable pageable) {
        List<DiscountCard> cardsWithSortForRent = discountCardRepository.findCardsWithSortForRent(discountMin, discountMax, pageable);
        return cardsWithSortForRent.stream()
                .map(discountCard -> modelMapper.map(discountCard, DiscountCardDto.class))
                .collect(Collectors.toList());
    }

    private BigDecimal getAverageUserDiscount(Long userId) {
        return discountCardRepository.getAverageDiscountFromAllHimCardsByUser(userId).orElse(ZERO);
    }
}
