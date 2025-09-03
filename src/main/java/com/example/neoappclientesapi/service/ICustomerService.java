package com.example.neoappclientesapi.service;

import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;
import com.example.neoappclientesapi.dto.CustomerUpdateDTO;
import com.example.neoappclientesapi.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);

    void deleteCustomer(Integer id);

    void softDeleteCustomer(Integer id);

    Page<CustomerResponseDTO> findAllCustomers(Pageable pageable);

    CustomerResponseDTO updateCustomer(Integer id, CustomerUpdateDTO customerUpdateDTO);

    Page<CustomerResponseDTO> searchCustomer (
            String name,
            String cpf,
            String email,
            CustomerStatus status,
            Pageable pageable
    );
}
