package bumblebee.xchangepass.domain.user.entity;

import bumblebee.xchangepass.domain.user.entity.value.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Embedded
    private UserEmail userEmail;

    @Embedded
    private UserPassword userPwd;

    @Embedded
    private UserName userName;

    @Embedded
    private UserNickname userNickname;

    @Embedded
    private UserPhoneNumber userPhoneNumber;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "user_sex")
    @Enumerated(value = EnumType.STRING)
    private Sex userSex;

    @Column(name = "user_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role userType;

    @CreatedDate
    @Column(name = "user_join_date")
    private LocalDateTime userJoinDate;

    @Column(name = "user_delte_date")
    private LocalDateTime userDeleteDate;

    @Builder
    public User(String userEmail,
                String userPwd,
                String userName,
                String userNickname,
                String userPhoneNumber,
                Integer userAge,
                Sex userSex,
                PasswordEncoder passwordEncoder) {
        this.userEmail = new UserEmail(userEmail);
        this.userPwd = new UserPassword(userPwd, passwordEncoder);
        this.userName = new UserName(userName);
        this.userNickname = new UserNickname(userNickname);
        this.userPhoneNumber = new UserPhoneNumber(userPhoneNumber);
        this.userAge = userAge;
        this.userSex = userSex;
        this.userType = Role.ROLE_USER;
    }
}
