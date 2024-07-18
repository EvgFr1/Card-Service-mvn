package ru.cards.SpringCard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    public enum Status{
        CREATED, DELIVERED, RECEIVED
    }

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private PaymentSystem paymentSystem;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    private String panNumber;
    @Transient
    private String maskPanNumber;

    @ManyToOne
    private BankBranch currentLocation;
    @ManyToOne
    private Owner owner;


//    public String setMaskPanNumber(String panNumber){
//        maskPanNumber = panNumber.substring(0,6) + "******" + panNumber.substring(panNumber.length()-4);
//        return maskPanNumber;
//    }
    public String getMaskPanNumber(){
       return panNumber.substring(0,6) + "******" + panNumber.substring(panNumber.length()-4);
   }

}
