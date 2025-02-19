package bumblebee.xchangepass.domain.user.repository;

import bumblebee.xchangepass.domain.user.dto.request.UserUpdateRequest;
import bumblebee.xchangepass.domain.user.entity.QUser;
import bumblebee.xchangepass.domain.user.entity.User;
import bumblebee.xchangepass.global.error.ErrorCode;
import bumblebee.xchangepass.global.util.DuplicateCheckUtil;
import bumblebee.xchangepass.global.util.EntityUpdateUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private static final QUser USER = QUser.user;
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public void checkForDuplicateNickname(String userNickname, Long userId) {
        DuplicateCheckUtil.checkDuplicate(
                (value, id) -> queryFactory
                        .selectOne()
                        .from(USER)
                        .where(USER.userNickname.value.eq(value)
                                .and(USER.userId.ne(id)))
                        .fetchFirst() != null,
                userNickname,
                userId,
                ErrorCode.USER_DUPLICATE_NICK_NAME
        );
    }

    @Override
    public void checkForDuplicatePhoneNumber(String userPhoneNumber, Long userId) {
        DuplicateCheckUtil.checkDuplicate(
                (value, id) -> queryFactory
                        .selectOne()
                        .from(USER)
                        .where(USER.userPhoneNumber.value.eq(value)
                                .and(USER.userId.ne(id)))
                        .fetchFirst() != null,
                userPhoneNumber,
                userId,
                ErrorCode.USER_DUPLICATE_PHONE_NUMBER
        );
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateRequest updateRequest, Long userId) {

        User existingUser = Optional.ofNullable(queryFactory
                        .selectFrom(USER)
                        .where(USER.userId.eq(userId))
                        .fetchOne())
                .orElseThrow(ErrorCode.USER_NOT_FOUND::commonException);

        JPAUpdateClause updateClause = queryFactory.update(USER);

        EntityUpdateUtil.executeUpdateIfChanged(
                existingUser,
                updateRequest,
                updateClause,
                (isUpdated, clause) -> {
                    if (isUpdated) {
                        clause.where(USER.userId.eq(userId)).execute();
                    }
                },
                USER
        );
    }
}
