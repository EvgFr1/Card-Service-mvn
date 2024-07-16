package ru.cards.SpringCard.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Cards")
public class Card {

    @Id
    @GeneratedValue
    private Long id;

    public enum ProductType {
        PREMIUM, REGULAR, SALARY, CHILD
    }

    public enum PaymentSystem {
        MASTERCARD, VISA, MIR
    }

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private PaymentSystem paymentSystem;

    private String panNumber;
    private String status;
    private String currentLocation;
    @ManyToOne
    private Owner owner;
}
