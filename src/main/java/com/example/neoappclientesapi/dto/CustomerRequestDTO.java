package com.example.neoappclientesapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CustomerRequestDTO (

        @NotBlank(message = "Name can't be blank") String name,
        @NotBlank(message = "CpF can't be blank") String cpf,
        @Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String email,
        @NotBlank(message = "Phone can't be blank") String phone,
        @Past(message = "Birth date must be in the past") LocalDate birthDate

) {
}
