package com.exercise.cra.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car extends Auditable {
    @Id
    @GeneratedValue(generator = "car_seq")
    @SequenceGenerator(name = "car_seq", sequenceName = "car_seq", allocationSize = 1)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "model")
    private String model;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "color")
    private String color;

    @Column(name = "power")
    private String power;

    @Column(name = "engine_capacity")
    private String engineCapacity;

    @Column(name = "mileage")
    private String mileage;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_cars",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> supervisors;

    @Version
    private long version;
}
