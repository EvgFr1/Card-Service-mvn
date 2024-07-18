package ru.cards.SpringCard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cards.SpringCard.model.BankBranch;

public interface BankBranchRepository extends JpaRepository<BankBranch,Long> {

    //public BankBranch findByBankBranchId(Long bankBranchId);
    //public BankBranch findByName(String name);
}
