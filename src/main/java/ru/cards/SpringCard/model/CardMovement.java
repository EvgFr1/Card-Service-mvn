package ru.cards.SpringCard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CardMovement {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Card card;
    private String fromLocation;
    private String toLocation;



}
