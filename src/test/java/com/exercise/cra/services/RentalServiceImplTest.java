package com.exercise.cra.services;



import com.exercise.cra.entities.Rental;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableJpaAuditing
@RunWith(SpringRunner.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:drop.sql"})
public class RentalServiceImplTest {
    @Autowired
    RentalServiceImpl rentalService;

    @Test
    public void getNumberOfRentedCarsBetween() {
        //given
        LocalDate  startDate = LocalDate.of(1998,2,1);
        LocalDate  endDate = LocalDate.of(1998,4,1);
        for (int i = 0; i < 10; i++){
            rentalService.add(Rental.builder()
                    .rentStart(startDate)
                    .rentEnd(endDate).build());
        }
        //when
        int countOfRentedCars = rentalService.getNumberOfRentedCarsBetween(startDate, endDate);
        //then
        assertEquals(10, countOfRentedCars);
    }
}