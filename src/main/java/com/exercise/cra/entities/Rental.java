package com.exercise.cra.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rentals")
public class Rental extends Auditable {
    @Id
    @GeneratedValue(generator = "rental_seq")
    @SequenceGenerator(name = "rental_seq", sequenceName = "rental_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "rent_car_id")
    private Car rentCar;

    @Column(name = "rent_start_date")
    private LocalDate rentStart;

    @ManyToOne
    @JoinColumn(name = "pickup_facility_id")
    private Facility pickupFacility;

    @Column(name = "rent_end_date")
    private LocalDate rentEnd;

    @ManyToOne
    @JoinColumn(name = "return_facility_id")
    private Facility returnFacility;


    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @Version
    private long version;
}
