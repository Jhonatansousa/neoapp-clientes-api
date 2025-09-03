package com.example.neoappclientesapi.repository;

import com.example.neoappclientesapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findByCpf(String cpf);
}
