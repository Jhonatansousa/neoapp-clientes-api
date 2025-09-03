package com.example.neoappclientesapi.service;

import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);

    void deleteCustomer(Integer id);

    void softDeleteCustomer(Integer id);

    Page<CustomerResponseDTO> findAllCustomers(Pageable pageable);
}
