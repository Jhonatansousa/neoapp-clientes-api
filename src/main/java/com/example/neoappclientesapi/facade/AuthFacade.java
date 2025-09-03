package com.example.neoappclientesapi.facade;

import com.example.neoappclientesapi.dto.LoginRequestDTO;
import com.example.neoappclientesapi.dto.RegisterRequestDTO;
import com.example.neoappclientesapi.securirty.AuthToken;
import com.example.neoappclientesapi.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final IAuthService authService;

    public AuthToken registerAndLogin(RegisterRequestDTO request) {
        authService.register(request);
        return authService.login(new LoginRequestDTO(request.getEmail(), request.getPassword()));
    }
}
