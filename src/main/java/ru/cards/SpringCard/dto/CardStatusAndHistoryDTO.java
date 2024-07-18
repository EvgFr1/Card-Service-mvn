package ru.cards.SpringCard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.cards.SpringCard.model.Card;
import ru.cards.SpringCard.model.CardMovement;

import java.util.List;

@Data
@AllArgsConstructor
public class CardStatusAndHistoryDTO {

    private Card.Status status;
    private List<CardMovement> movements;

}
