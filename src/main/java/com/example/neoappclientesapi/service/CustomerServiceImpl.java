package com.example.neoappclientesapi.service;

import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;
import com.example.neoappclientesapi.entity.Customer;
import com.example.neoappclientesapi.entity.CustomerStatus;
import com.example.neoappclientesapi.exception.DuplicatedResourceException;
import com.example.neoappclientesapi.exception.ResourceNotFoundException;
import com.example.neoappclientesapi.mapper.CustomerMapper;
import com.example.neoappclientesapi.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public void deleteCustomer(Integer id) {
        Customer customerToDelete = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer with id " + id + " not found")
        );
        repo.delete(customerToDelete);
    }

    @Override
    public void softDeleteCustomer(Integer id) {
        Customer customerToDelete = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer with id " + id + " not found")
        );
        customerToDelete.setStatus(CustomerStatus.INACTIVE);
        repo.save(customerToDelete);
    }

    @Override
    public Page<CustomerResponseDTO> findAllCustomers(Pageable pageable) {
        Page<Customer> customerPage = repo.findAll(pageable);
        return customerPage.map(mapper::toResponseDTO);
    }


}
