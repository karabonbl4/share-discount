package com.senla.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_card")
@NamedEntityGraph(name = "fetch.rent.card-tenant",
        attributeNodes = {@NamedAttributeNode(value = "card"),
                          @NamedAttributeNode(value = "tenant")})
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rent_start")
    private LocalDateTime rentStart;

    @Column(name = "rent_end")
    private LocalDateTime rentEnd;

    @ManyToOne(cascade = CascadeType.DETACH,  fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private DiscountCard card;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User tenant;

}
