package com.example.swapSafe.repository;

import com.example.swapSafe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByLinkedWalletAddresses_WalletAddress(String walletAddress);

    @Override
    List<User> findAll();
}
