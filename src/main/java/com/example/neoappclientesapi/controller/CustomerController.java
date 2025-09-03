package com.example.neoappclientesapi.controller;

import com.example.neoappclientesapi.dto.APIResponse;
import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;
import com.example.neoappclientesapi.entity.Customer;
import com.example.neoappclientesapi.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService service;

    @PostMapping("/create")
    public ResponseEntity<APIResponse<CustomerResponseDTO>> create (@RequestBody @Valid CustomerRequestDTO dto,
                                            UriComponentsBuilder uriBuilder) {

        CustomerResponseDTO customer = service.createCustomer(dto);

        URI location = uriBuilder.path("/customers/{id}").buildAndExpand(customer.id()).toUri();

        APIResponse<CustomerResponseDTO> apiResponse = APIResponse.<CustomerResponseDTO>builder()
                .status("SUCCESS")
                .results(customer)
                .build();

        return ResponseEntity.created(location).body(apiResponse);
    }

}
