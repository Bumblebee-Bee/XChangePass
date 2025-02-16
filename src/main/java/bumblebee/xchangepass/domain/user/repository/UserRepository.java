package bumblebee.xchangepass.domain.user.repository;

import bumblebee.xchangepass.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT m FROM User m WHERE m.userEmail.value=:email")
    Optional<User> findByUserEmail(String email);

    @Query(value = "SELECT m FROM User m WHERE m.userNickname.value = :nickName")
    User findByUserNickName(String nickName);

}
