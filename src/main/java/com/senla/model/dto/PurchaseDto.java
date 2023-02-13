package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime transactionDate;
    private BigDecimal sum;
    @JsonIgnore
    private UserDto userId;
    @JsonIgnore
    private DiscountCardDto cardId;
    @JsonIgnore
    private CouponDto couponId;
}
