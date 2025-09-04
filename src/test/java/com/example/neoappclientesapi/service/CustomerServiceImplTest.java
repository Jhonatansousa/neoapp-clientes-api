package com.example.neoappclientesapi.service;


import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;
import com.example.neoappclientesapi.entity.Customer;
import com.example.neoappclientesapi.mapper.CustomerMapper;
import com.example.neoappclientesapi.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepo repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerServiceImpl service;

    private CustomerRequestDTO requestDTO;
    private Customer customer;
    private CustomerResponseDTO customerResponseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new CustomerRequestDTO("nome teste",
                "99999999999",
                "email@test.com",
                "+55 24 9 9999-9999",
                LocalDate.of(1996,4,8));


        customer = new Customer();
        customer.setId(1);
        customer.setCpf(requestDTO.cpf());

        customerResponseDTO = new CustomerResponseDTO(
                1,
                "jhonatan",
                "99999999999",
                "email@test.com",
                "+55 24 9 9999-9999",
                LocalDate.of(1999,9,9), null, null, 29);
    }


    @Test
    @DisplayName("Should be able to create a new customer when cpf doesn't exist")
    void shouldBeAbleToCreateNewCustomerWhenCpfDoesNotExist() {
        when(repository.findByCpf(anyString())).thenReturn(null);
        when(mapper.toEntity(any(CustomerRequestDTO.class))).thenReturn(customer);
        when(repository.save(any(Customer.class))).thenReturn(customer);
        when(mapper.toResponseDTO(any(Customer.class))).thenReturn(customerResponseDTO);

        CustomerResponseDTO response = service.createCustomer(requestDTO);

        assertThat(response).isNotNull();
        assertThat(response.cpf()).isEqualTo(requestDTO.cpf());
        verify(repository, times(1)).findByCpf(requestDTO.cpf());
        verify(repository, times(1)).save(any(Customer.class));
        verify(mapper, times(1)).toResponseDTO(customer);


    }


}
