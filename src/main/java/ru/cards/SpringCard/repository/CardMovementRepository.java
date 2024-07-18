package ru.cards.SpringCard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cards.SpringCard.model.CardMovement;

import java.util.List;
import java.util.Optional;

public interface CardMovementRepository extends JpaRepository<CardMovement, Long> {
    List<CardMovement> findByCardId(Long cardId);
}
