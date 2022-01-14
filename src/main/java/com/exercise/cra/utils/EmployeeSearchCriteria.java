package com.exercise.cra.utils;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchCriteria {
    private Long facilityId;
    private Long carId;
    private Position position;
}
