package com.example.swapSafe.repository;

import com.example.swapSafe.model.DepositTransaction;
import com.example.swapSafe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Long> {

    List<DepositTransaction> findByWalletAddress(String walletAddress);
    List<DepositTransaction> findByUserId(Long userId);

}
