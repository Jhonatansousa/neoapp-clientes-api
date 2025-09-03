package com.example.neoappclientesapi.repository;

import com.example.neoappclientesapi.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<AppUser, UUID> {

    AppUser findByEmail(String email);
}
