package bumblebee.xchangepass.domain.wallet.repository;

import bumblebee.xchangepass.domain.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>, WalletRepositoryCustom{


}
