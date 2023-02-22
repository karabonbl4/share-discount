package com.senla.service.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private BigDecimal discount;
    private Boolean used;
    private TrademarkDto trademarkId;
    @JsonIgnore
    private List<PurchaseDto> purchases;
    @JsonIgnore
    private List<UserDto> users;
}
