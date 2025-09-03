package com.example.neoappclientesapi.dto;

import com.example.neoappclientesapi.entity.CustomerStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerResponseDTO(
        Integer id,
        String name,
        String cpf,
        String email,
        String phone,
        LocalDate birthDate,
        CustomerStatus status,
        LocalDateTime createdAt,
        Integer age
) {
}
