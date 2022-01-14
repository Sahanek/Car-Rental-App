package com.exercise.cra.controllers;

import com.exercise.cra.dao.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    CarRepository carRepository;
}
