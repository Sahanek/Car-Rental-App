package com.exercise.cra.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "facilities")
public class Facility extends Auditable {
    @Id
    @GeneratedValue(generator = "facility_seq")
    @SequenceGenerator(name = "facility_seq", sequenceName = "facility_seq", allocationSize = 1)
    private long id;

    @Column(name = "city")
    private String city;

    @Column(name = "postcode")
    private String postCode;

    @Column(name = "street")
    private String street;

    @Column(name = "house_num")
    private String houseNumber;

    @OneToMany(mappedBy = "facility", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @Column(name = "employees")
    private List<Employee> employees;

    @Version
    private long version;
}
