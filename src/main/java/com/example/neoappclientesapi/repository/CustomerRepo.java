package com.example.neoappclientesapi.repository;

import com.example.neoappclientesapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {
    Customer findByCpf(String cpf);

}
