package com.example.neoappclientesapi.dto;

import java.time.LocalDate;

public record CustomerUpdateDTO(
        String name,
        String email,
        String phone,
        LocalDate birthDate
) {
}
