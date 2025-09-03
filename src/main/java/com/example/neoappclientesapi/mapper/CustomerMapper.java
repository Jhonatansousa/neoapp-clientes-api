package com.example.neoappclientesapi.mapper;

import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;
import com.example.neoappclientesapi.entity.Customer;
import com.example.neoappclientesapi.entity.CustomerStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setCpf(dto.cpf());
        customer.setEmail(dto.email());
        customer.setPhone(dto.phone());
        customer.setBirthDate(dto.birthDate());
        customer.setStatus(CustomerStatus.ACTIVE);

        return customer;
    }

    public CustomerResponseDTO toResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getBirthDate(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getAge()
        );
    }
}
