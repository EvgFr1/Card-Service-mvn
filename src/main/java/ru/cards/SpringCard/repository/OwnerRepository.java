package ru.cards.SpringCard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cards.SpringCard.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    //public Owner findByOwnerId(Long ownerId);
}
