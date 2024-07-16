package ru.cards.SpringCard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cards.SpringCard.model.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    //public Card findByCardId(Long cardId);
}
