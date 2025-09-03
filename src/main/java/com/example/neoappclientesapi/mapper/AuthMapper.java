package com.example.neoappclientesapi.mapper;

import com.example.neoappclientesapi.dto.RegisterRequestDTO;
import com.example.neoappclientesapi.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;

    public AppUser toEntity (RegisterRequestDTO request) {
        AppUser newUser = new AppUser();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        return newUser;
    }
}
