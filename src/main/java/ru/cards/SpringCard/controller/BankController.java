package ru.cards.SpringCard.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cards.SpringCard.model.BankBranch;
import ru.cards.SpringCard.model.Card;
import ru.cards.SpringCard.model.CardMovement;
import ru.cards.SpringCard.model.Owner;
import ru.cards.SpringCard.repository.OwnerRepository;
import ru.cards.SpringCard.service.BankService;

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
    public Card moveCard(@RequestParam Long cardId, @RequestParam Long toBranchId){
        return bankService.moveCard(cardId,toBranchId);
        //return "Карта отправлена в другое банковское отделение";
    }

    @PutMapping("cards/recive")
    public Card reciveCard (@RequestParam Long cardId){
        return bankService.reciveCard(cardId);

    }




}
