package com.example.neoappclientesapi.service;

import com.example.neoappclientesapi.dto.LoginRequestDTO;
import com.example.neoappclientesapi.dto.RegisterRequestDTO;
import com.example.neoappclientesapi.securirty.AuthToken;

public interface IAuthService {

    AuthToken login(LoginRequestDTO loginRequestDTO);
    void register(RegisterRequestDTO registerRequestDTO);
}
