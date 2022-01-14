package com.exercise.cra.utils;

import com.exercise.cra.entities.Car;
import com.exercise.cra.entities.Employee;
import com.exercise.cra.entities.Facility;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeSearchSpecification {
    public Specification<Employee> findByCriteria(final EmployeeSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchCriteria.getCarId() != null) {
                Join<Employee, Car> cars = root.join("cars");
                predicates.add(cars.in(searchCriteria.getCarId()));
            }
            if (searchCriteria.getFacilityId() != null) {
                Join<Employee, Facility> facilities = root.join("facility");
                predicates.add(facilities.in(searchCriteria.getFacilityId()));
            }
            if (searchCriteria.getPosition() != null) {
                predicates.add(criteriaBuilder.like(root.get("position").as(String.class), searchCriteria.getPosition().name()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}