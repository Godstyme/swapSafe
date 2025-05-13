package com.example.swapSafe.repository;

import com.example.swapSafe.model.User;
import com.example.swapSafe.model.UserAssetBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAssetBalanceRepository extends JpaRepository<UserAssetBalance, Long> {
    Optional<UserAssetBalance> findByUserAndNetwork(User user, String network);
}
