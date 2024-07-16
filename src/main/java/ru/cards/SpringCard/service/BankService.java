package ru.cards.SpringCard.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cards.SpringCard.model.BankBranch;
import ru.cards.SpringCard.model.Card;
import ru.cards.SpringCard.model.CardMovement;
import ru.cards.SpringCard.model.Owner;
import ru.cards.SpringCard.repository.BankBranchRepository;
import ru.cards.SpringCard.repository.CardMovementRepository;
import ru.cards.SpringCard.repository.CardRepository;
import ru.cards.SpringCard.repository.OwnerRepository;
import java.util.Random;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BankService {

    private CardRepository cardRepository;
    private BankBranchRepository bankBranchRepository;
    private OwnerRepository ownerRepository;
    private CardMovementRepository cardMovementRepository;

    private String generateCardNumber(Card.PaymentSystem paymentSystem){
        String bin = "";
        String customerIdentifier  = "";
        Random random = new Random();
        switch (paymentSystem){
            case MIR:
                bin = "220" + random.nextInt(5) + random.nextInt(100);
                break;
            case MASTERCARD:
                bin = "5" + (1 + random.nextInt(5)) + random.nextInt(10000);
                break;
            case VISA:
                bin = "4" + random.nextInt(100000);
                break;
        }

        customerIdentifier = String.valueOf(random.nextLong(10000000000L));

        return bin + customerIdentifier;

    }


    public Owner registerOwner(Owner owner){
        return ownerRepository.save(owner);
    }

    public BankBranch createBranch(BankBranch bankBranch){
        return bankBranchRepository.save(bankBranch);
    }

    public Card createCard(Card card, Long ownerId, Long bankBranchId){
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Клиент не найден"));
        BankBranch bankBranch = bankBranchRepository.findById(bankBranchId).orElseThrow(() -> new IllegalArgumentException("Банковское отделение не найдено"));

       if (!bankBranch.isMainBranch()) {
           throw new IllegalArgumentException("Карта может быть создана тольков главном отделении");
       }

        card.setOwner(owner);
        card.setCurrentLocation(bankBranch.getName());
        card.setPanNumber(generateCardNumber(card.getPaymentSystem()));
        card.setStatus("Создана");

        return cardRepository.save(card);
    }

    public Card moveCard(Long cardId, Long toBranchId){
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("Карта не найдена"));
        BankBranch fromBranch = bankBranchRepository.findByName(card.getCurrentLocation());
        BankBranch toBranch = bankBranchRepository.findById(toBranchId).orElseThrow(() -> new IllegalArgumentException("Банковское отделение не найдено"));

        if(card.getCurrentLocation().equals(toBranch.getName())){
            throw new IllegalArgumentException("Нельзя отправить карту в отделение банка в котором она уже находится");
        }

        card.setCurrentLocation(toBranch.getName());
        card.setStatus("В доставке");
        cardRepository.save(card);

        CardMovement cardMovement = new CardMovement();
        cardMovement.setCard(card);
        cardMovement.setFromLocation(fromBranch.getName());
        cardMovement.setToLocation(toBranch.getName());
        cardMovementRepository.save(cardMovement);
        return card;
    }

    public Card reciveCard(Long cardId){

        Card card = cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("Карта не найдена"));
        BankBranch bankBranch = bankBranchRepository.findByName(card.getCurrentLocation());

        if(bankBranch.isMainBranch()){
            throw new IllegalArgumentException("Карта находится в главном отделении. Оформите доставку в дочернее отделение.");
        }

        card.setCurrentLocation("У клиента");
        card.setStatus("Получена");
        return cardRepository.save(card);


    }

}
