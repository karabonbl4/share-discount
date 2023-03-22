package com.senla.model.dto.mapper;

import com.senla.model.dto.RentDto;
import com.senla.model.entity.DiscountCard;
import com.senla.model.entity.Rent;
import com.senla.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomRentDtoMapper {

    private final ModelMapper modelMapper;

    public Rent map(RentDto rentDto) {
        Rent mappedRent = Rent.builder()
                .id(rentDto.getId())
                .rentEnd(rentDto.getRentEnd())
                .rentStart(rentDto.getRentStart())
                .build();
        if (rentDto.getTenant() != null){
            mappedRent.setTenant(modelMapper.map(rentDto.getTenant(), User.class));
        }
        if (rentDto.getRentCard() != null){
            mappedRent.setCard(modelMapper.map(rentDto.getRentCard(), DiscountCard.class));
        }
        return mappedRent;
    }
}
