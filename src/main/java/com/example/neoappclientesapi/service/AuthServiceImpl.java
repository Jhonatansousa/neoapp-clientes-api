package com.example.neoappclientesapi.service;

import com.example.neoappclientesapi.dto.LoginRequestDTO;
import com.example.neoappclientesapi.dto.RegisterRequestDTO;
import com.example.neoappclientesapi.dto.TokenDataDTO;
import com.example.neoappclientesapi.entity.AppUser;
import com.example.neoappclientesapi.exception.DuplicatedResourceException;
import com.example.neoappclientesapi.exception.InvalidCredentialsException;
import com.example.neoappclientesapi.mapper.AuthMapper;
import com.example.neoappclientesapi.repository.UserRepo;
import com.example.neoappclientesapi.security.AuthToken;
import com.example.neoappclientesapi.security.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepo userRepo;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthToken login(LoginRequestDTO dto) {
        AppUser res = userRepo.findByEmail(dto.getEmail());
        if (res == null || !passwordEncoder.matches(dto.getPassword(), res.getPassword())) {
            throw new InvalidCredentialsException("Email or password invalid");
        }

        TokenDataDTO tokenData = new TokenDataDTO(res.getEmail() ,"USER");

        return TokenUtil.encodeToken(tokenData);
    }

    @Override
    public void register(RegisterRequestDTO registerRequestDTO) {
        if (userRepo.findByEmail(registerRequestDTO.getEmail()) != null) {
            throw new DuplicatedResourceException("Email already exists");
        }

        userRepo.save(authMapper.toEntity(registerRequestDTO));
    }
}
