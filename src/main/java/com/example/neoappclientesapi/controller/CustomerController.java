package com.example.neoappclientesapi.controller;

import com.example.neoappclientesapi.dto.APIResponse;
import com.example.neoappclientesapi.dto.CustomerRequestDTO;
import com.example.neoappclientesapi.dto.CustomerResponseDTO;
import com.example.neoappclientesapi.dto.CustomerUpdateDTO;
import com.example.neoappclientesapi.entity.CustomerStatus;
import com.example.neoappclientesapi.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @PatchMapping("/status/customer/{id}")
    public ResponseEntity<APIResponse<Void>> softDeleteCustomer(@PathVariable Integer id) {
        service.softDeleteCustomer(id);

        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status("SUCCESS")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }


    @GetMapping("/customers")
    public ResponseEntity<APIResponse<Page<CustomerResponseDTO>>> findAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable) {

        Page<CustomerResponseDTO> customerPage = service.findAllCustomers(pageable);

        APIResponse<Page<CustomerResponseDTO>> apiResponse = APIResponse.<Page<CustomerResponseDTO>>builder()
                .status("SUCCESS")
                .results(customerPage)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PutMapping("/customer/{id}")
    public ResponseEntity<APIResponse<CustomerResponseDTO>> updateCustomer(@PathVariable Integer id, @RequestBody @Valid CustomerUpdateDTO dto) {
        CustomerResponseDTO customer = service.updateCustomer(id, dto);

        APIResponse<CustomerResponseDTO> apiResponse = APIResponse.<CustomerResponseDTO>builder()
                .status("SUCCES")
                .results(customer)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }



    @GetMapping("/customers/search")
    public ResponseEntity<APIResponse<Page<CustomerResponseDTO>>> searchCustomer(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) CustomerStatus status,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CustomerResponseDTO> customerPage = service.searchCustomer(name, cpf, email, status, pageable);

        APIResponse<Page<CustomerResponseDTO>> apiResponse = APIResponse.<Page<CustomerResponseDTO>>builder()
                .status("SUCCESS")
                .results(customerPage)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
