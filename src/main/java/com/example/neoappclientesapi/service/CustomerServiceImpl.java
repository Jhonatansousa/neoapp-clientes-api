package com.example.neoappclientesapi.service;

import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;
import com.example.neoappclientesapi.exception.DuplicatedResourceException;
import com.example.neoappclientesapi.mapper.CustomerMapper;
import com.example.neoappclientesapi.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepo repo;
    private final CustomerMapper mapper;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        if (repo.findByCpf(customerRequestDTO.cpf()) != null) {
            throw new DuplicatedResourceException("there is already a customer with cpf: " + customerRequestDTO.cpf());
        }
        return mapper.toResponseDTO(repo.save(mapper.toEntity(customerRequestDTO)));
    }
}
