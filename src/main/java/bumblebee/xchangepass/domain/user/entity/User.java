package bumblebee.xchangepass.domain.user.entity;

import bumblebee.xchangepass.domain.user.dto.request.UserUpdateRequest;
import bumblebee.xchangepass.domain.user.entity.value.UserEmail;
import bumblebee.xchangepass.domain.user.entity.value.UserNickname;
import bumblebee.xchangepass.domain.user.entity.value.UserPassword;
import bumblebee.xchangepass.domain.user.entity.value.UserPhonenumber;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Embedded
    private UserNickname userNickname;

    @Embedded
    private UserPhonenumber userPhonenumber;

    @Embedded
    private UserEmail userEmail;

    @Embedded
    private UserPassword userPwd;

    @Column(name = "user_sex", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Sex userSex;

    @Column(name = "user_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role userRole;

    @Column(name = "birth_day")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime deleteAt;

    @Builder
    public User(String userNickname, String userPhonenumber, LocalDate birthDay, Sex userSex, Role userRole, String userEmail, String userPwd, PasswordEncoder passwordEncoder) {
        this.userNickname = new UserNickname(userNickname);
        this.userPhonenumber = new UserPhonenumber(userPhonenumber);
        this.userEmail = new UserEmail(userEmail);
        this.userPwd = new UserPassword(userPwd, passwordEncoder);
        this.birthDay = birthDay;
        this.userSex = userSex;
        this.userRole = userRole;
    }

    public String getUserNickname(){
        return this.userNickname.getValue();
    }
    public String getUserPhonenumber(){
        return this.userPhonenumber.getValue();
    }
    public String getUserEmail(){
        return this.userEmail.getValue();
    }
    public String getUserPwd(){
        return this.userPwd.getValue();
    }

    public void updateUser(final UserUpdateRequest userUpdateRequest){
        this.userNickname = new UserNickname(userUpdateRequest.nickName());
        this.userPhonenumber = new UserPhonenumber(userUpdateRequest.phoneNumber());
        this.userEmail = new UserEmail(userUpdateRequest.email());
        this.userSex = userUpdateRequest.sex();
        this.birthDay = userUpdateRequest.birthDay();
    }

    public void changeEmail(String email){
        this.userEmail =  new UserEmail(email);
    }
}
