package com.exercise.cra.services;

import com.exercise.cra.dao.EmployeeRepository;
import com.exercise.cra.entities.Employee;
import com.exercise.cra.utils.EmployeeSearchCriteria;
import com.exercise.cra.utils.EmployeeSearchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository empRepo;

    private final EmployeeSearchSpecification empSearchSpec;

    @Override
    public Employee findById(long id) {
        return empRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    @Transactional
    public List<Employee> findByCriteria(EmployeeSearchCriteria employeeSearchCriteria) {
        return empRepo.findAll(empSearchSpec.findByCriteria(employeeSearchCriteria));
    }

    @Transactional
    public Employee add(Employee employee) {
        return empRepo.save(employee);
    }
}
