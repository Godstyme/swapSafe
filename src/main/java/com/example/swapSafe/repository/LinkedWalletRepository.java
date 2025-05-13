package com.example.swapSafe.repository;
import com.example.swapSafe.model.LinkedWalletAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  LinkedWalletRepository extends JpaRepository<LinkedWalletAddress,Long> {

//Optional<LinkedWalletAddress> findByUserIdAndNetwork(@Param("userId") Long userId, @Param("network") String network);
    Optional<LinkedWalletAddress> findByUserIdAndNetwork(Long userId, String network);

    Optional<LinkedWalletAddress> findByWalletAddressAndNetwork(String walletAddress, String network);

}
