package com.exercise.cra.entities;

import com.exercise.cra.utils.Position;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee extends Auditable {
    @Id
    @GeneratedValue(generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    private long id;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone")
    private String phoneNumber;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_cars",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Version
    private long version;
}
