package com.happiday.Happi_Day.domain.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username; // 이메일
    private String password;
    private String nickname; // 닉네임
    private String realname; // 실명
    private String phone;
    private RoleType role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public static CustomUserDetails fromEntity(User entity) {
        return CustomUserDetails.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .realname(entity.getRealname())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .build();
    }

    // 회원가입하는 유저를 USER 권한으로 저장
    public User newEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .realname(realname)
                .phone(phone)
                .role(role)
                .termsAt(LocalDateTime.now())
                .isTermsAgreed(true)
                .build();

    }

}
