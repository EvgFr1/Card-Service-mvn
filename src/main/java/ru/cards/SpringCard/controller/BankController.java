package ru.cards.SpringCard.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cards.SpringCard.dto.CardStatusAndHistoryDTO;
import ru.cards.SpringCard.model.BankBranch;
import ru.cards.SpringCard.model.Card;
import ru.cards.SpringCard.model.CardMovement;
import ru.cards.SpringCard.model.Owner;
import ru.cards.SpringCard.service.BankService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/bank")
public class BankController {

    private BankService bankService;

    @PostMapping("/register")
    public Owner registerOwner(@RequestBody Owner owner){
        return bankService.registerOwner(owner);
        //return "Клиент успешно зарегистрирован";
    }

    @PostMapping("/create_branch")
    public BankBranch createBranch(@RequestBody BankBranch bankBranch){
        return bankService.createBranch(bankBranch);
        //return "Отделение банка успешно создано";
    }

    @PostMapping("/cards/create")
    public Card createCard(@RequestBody Card card, @RequestParam Long ownerId, @RequestParam Long bankBranchId){
        return bankService.createCard(card, ownerId, bankBranchId);
        //return "Карта успешно оформлена";
    }

    @PutMapping("/cards/move")
    public BankBranch moveCard(@RequestParam Long cardId, @RequestParam Long toBranchId){
        return bankService.moveCard(cardId,toBranchId);
        //return "Карта отправлена в другое банковское отделение";
    }

    @PutMapping("/cards/receive")
    public Card receiveCard(@RequestParam Long cardId){
        return bankService.receiveCard(cardId);

    }

    @GetMapping("/cards/{cardId}/history")
    public List<CardMovement> getCardHistory(@PathVariable Long cardId) {
        return bankService.getCardHistory(cardId);
    }
    @GetMapping("/cards/{cardId}/status")
    public CardStatusAndHistoryDTO getCardStatus(@PathVariable Long cardId){
        return bankService.getCardStatusAndHistory(cardId);
    }


////    @GetMapping("/cards/{cardId}/status")
////    public Card getCardStatus(@PathVariable Long cardId){
////        return bankService.getCardStatus(cardId);
////    }
//    @GetMapping("/cards/{cardId}/status")
//    public Card.Status getCardStatus(@PathVariable Long cardId){
//        return bankService.getCardStatus(cardId);
//    }





}
