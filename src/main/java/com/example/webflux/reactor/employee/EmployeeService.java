package com.example.webflux.reactor.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public void create(Employee e) {
        employeeRepository.save(e).subscribe();
    }

    @Override
    public Mono<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Flux<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public Flux<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Mono<Employee> update(Employee e) {
        return employeeRepository.save(e);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return employeeRepository.deleteById(id);
    }
}
