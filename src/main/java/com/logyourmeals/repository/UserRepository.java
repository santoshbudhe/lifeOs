package com.logyourmeals.repository;


import com.logyourmeals.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {


    // Find user by phone number (useful for WhatsApp flow)
    Optional<User> findByPhoneNumber(String phoneNumber);
}


