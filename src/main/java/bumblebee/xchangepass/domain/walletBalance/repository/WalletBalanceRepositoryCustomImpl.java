package bumblebee.xchangepass.domain.walletBalance.repository;

import bumblebee.xchangepass.domain.wallet.entity.QWallet;
import bumblebee.xchangepass.domain.wallet.entity.Wallet;
import bumblebee.xchangepass.domain.walletBalance.entity.QWalletBalance;
import bumblebee.xchangepass.domain.walletBalance.entity.WalletBalance;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Currency;
import java.util.List;

@Repository
public class WalletBalanceRepositoryCustomImpl implements WalletBalanceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public WalletBalanceRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<WalletBalance> findByWalletId(Long walletId) {
        QWalletBalance walletBalance = QWalletBalance.walletBalance;

        return queryFactory
                .selectFrom(walletBalance)
                .where(walletBalance.wallet.walletId.eq(walletId))
                .fetch();
    }

    @Override
    public WalletBalance findByWalletIdAndCurrency(Long walletId, Currency currency) {
        QWalletBalance walletBalance = QWalletBalance.walletBalance;

        return queryFactory
                .selectFrom(walletBalance)
                .where(walletBalance.wallet.walletId.eq(walletId), walletBalance.currency.eq(currency))
                .fetchOne();
    }
}
