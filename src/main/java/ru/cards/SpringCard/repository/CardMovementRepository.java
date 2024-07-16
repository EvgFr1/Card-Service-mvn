package ru.cards.SpringCard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cards.SpringCard.model.CardMovement;

public interface CardMovementRepository extends JpaRepository<CardMovement, Long> {}
