package ru.cards.SpringCard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "BankBranchList")
public class BankBranch {

    @Id
    @GeneratedValue
    private Long id;
    private boolean mainBranch;
    private String name;
    private String bankAddress;



}
