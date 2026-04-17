package com.lifeos.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lifeos.entity.User;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {


    // Find user by phone number (useful for WhatsApp flow)
    Optional<User> findByPhoneNumber(String phoneNumber);
}


