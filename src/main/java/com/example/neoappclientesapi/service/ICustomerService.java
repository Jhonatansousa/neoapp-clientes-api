package com.example.neoappclientesapi.service;

import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;

public interface ICustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);
}
