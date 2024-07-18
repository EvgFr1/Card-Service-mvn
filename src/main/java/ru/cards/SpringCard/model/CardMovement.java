package ru.cards.SpringCard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class CardMovement {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Card card;

    @ManyToOne
    private BankBranch fromLocation;

    @ManyToOne
    private BankBranch toLocation;





}
