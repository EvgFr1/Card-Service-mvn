package ru.cards.SpringCard.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Owners")
public class Owner {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate DateOfBirth;
    private String ownerAddress;


}
