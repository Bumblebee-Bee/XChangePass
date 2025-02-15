package bumblebee.xchangepass.domain.wallet.repository;

import bumblebee.xchangepass.domain.wallet.entity.Wallet;

import java.util.List;

public interface WalletRepositoryCustom {

    Wallet findByUserId(Long userId);

}
