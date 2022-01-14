package com.exercise.cra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client extends Auditable {
    @Id
    @GeneratedValue(generator = "client_seq")
    @SequenceGenerator(name = "client_seq", sequenceName = "client_seq", allocationSize = 1)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @Version
    private long version;
}
