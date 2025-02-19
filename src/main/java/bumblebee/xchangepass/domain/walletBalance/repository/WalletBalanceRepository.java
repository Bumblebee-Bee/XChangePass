package bumblebee.xchangepass.domain.walletBalance.repository;

import bumblebee.xchangepass.domain.walletBalance.entity.WalletBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletBalanceRepository extends JpaRepository<WalletBalance, Long>, WalletBalanceRepositoryCustom {
}
