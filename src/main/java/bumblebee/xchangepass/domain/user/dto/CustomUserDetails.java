package bumblebee.xchangepass.domain.user.dto;

import bumblebee.xchangepass.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomUserDetails implements UserDetails {

    private final User user;

    private Map<String, Object> attributes;

    // 일반 로그인
    public CustomUserDetails(User user){
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // member.getUserRole().name()의 값을 권한으로 추가
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));

        return authorities;
    }


    @Override
    public String getPassword() {

        return user.getUserPwd();
    }

    public Long getId(){ //member의 id값 가져오기

        return user.getUserId();
    }

    @Override
    public String getUsername() {

        return user.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
