package com.example.neoappclientesapi.controller;

import com.example.neoappclientesapi.dto.APIResponse;
import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;
import com.example.neoappclientesapi.entity.Customer;
import com.example.neoappclientesapi.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


    @DeleteMapping("/customers/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id) {

        service.deleteCustomer(id);

        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status("SUCCESS")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/customer/{id}")
    public ResponseEntity<APIResponse<Void>> deleteCustomer(@PathVariable Integer id) {
        service.softDeleteCustomer(id);

        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status("SUCCESS")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }


}
