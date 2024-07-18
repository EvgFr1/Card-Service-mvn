package ru.cards.SpringCard.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cards.SpringCard.dto.CardStatusAndHistoryDTO;
import ru.cards.SpringCard.model.BankBranch;
import ru.cards.SpringCard.model.Card;
import ru.cards.SpringCard.model.CardMovement;
import ru.cards.SpringCard.model.Owner;
import ru.cards.SpringCard.repository.BankBranchRepository;
import ru.cards.SpringCard.repository.CardMovementRepository;
import ru.cards.SpringCard.repository.CardRepository;
import ru.cards.SpringCard.repository.OwnerRepository;

import java.util.List;
import java.util.Random;


@Service
@AllArgsConstructor
public class BankService {

    private CardRepository cardRepository;
    private BankBranchRepository bankBranchRepository;
    private OwnerRepository ownerRepository;
    private CardMovementRepository cardMovementRepository;

    private String generateCardNumber(Card.PaymentSystem paymentSystem){
        String bin = "";
        String customerIdentifier = "";
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
            default:
                throw new IllegalArgumentException("Платежная система не найдена");
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

        try {
            Card.ProductType.valueOf(card.getProductType().name());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неверно указан тип продукта: " + card.getProductType());
        }

        card.setOwner(owner);
        card.setCurrentLocation(bankBranch);
        card.setPanNumber(generateCardNumber(card.getPaymentSystem()));
        card.setMaskPanNumber(card.getPanNumber());
        card.setStatus(Card.Status.CREATED);

        return cardRepository.save(card);
    }

    public BankBranch moveCard(Long cardId, Long toBranchId){
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("Карта не найдена"));
        BankBranch fromBranch = card.getCurrentLocation();
        BankBranch toBranch = bankBranchRepository.findById(toBranchId).orElseThrow(() -> new IllegalArgumentException("Банковское отделение не найдено"));

        if(fromBranch.equals(toBranch)){
            throw new IllegalArgumentException("Нельзя отправить карту в отделение банка в котором она уже находится");
        }

        card.setCurrentLocation(toBranch);
        card.setStatus(Card.Status.DELIVERED);
        cardRepository.save(card);

        CardMovement cardMovement = new CardMovement();
        cardMovement.setCard(card);
        cardMovement.setFromLocation(fromBranch);
        cardMovement.setToLocation(toBranch);
        cardMovementRepository.save(cardMovement);
        return card.getCurrentLocation();
    }

    public Card receiveCard(Long cardId){

        Card card = cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("Карта не найдена"));
        BankBranch bankBranch = card.getCurrentLocation();

        if(bankBranch.isMainBranch()){
            throw new IllegalArgumentException("Карта находится в главном отделении. Оформите доставку в дочернее отделение.");
        }

        card.setCurrentLocation(null);
        card.setStatus(Card.Status.RECEIVED);
        return cardRepository.save(card);


    }

    public List<CardMovement> getCardHistory(Long cardId) {
        List<CardMovement> movements = cardMovementRepository.findByCardId(cardId);
        if (movements.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return movements;
    }

    public CardStatusAndHistoryDTO getCardStatusAndHistory(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<CardMovement> movements = cardMovementRepository.findByCardId(cardId);

        return new CardStatusAndHistoryDTO(card.getStatus(), movements);
    }


//    public Card.Status getCardStatus(Long cardId) {
//        Card card = cardRepository.findById(cardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Карта не найдена"));
//        return card.getStatus();
//    }
//
////    public Card getCardStatus(Long cardId) {
////        return cardRepository.findById(cardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Карта не найдена"));
////    }


}
