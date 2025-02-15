package bumblebee.xchangepass.domain.wallet.repository;

import bumblebee.xchangepass.domain.wallet.entity.QWallet;
import bumblebee.xchangepass.domain.wallet.entity.Wallet;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WalletRepositoryCustomImpl implements WalletRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public WalletRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Wallet findByUserId(Long userId) {
        QWallet wallet = QWallet.wallet;

        return queryFactory
                .selectFrom(wallet)
                .where(wallet.user.userId.eq(userId))
                .fetchOne();
    }
}
