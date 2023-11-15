package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.domain.entity.user.CustomUserDetails;
import com.happiday.Happi_Day.domain.entity.user.RoleType;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.entity.user.dto.UserLoginDto;
import com.happiday.Happi_Day.domain.entity.user.dto.UserRegisterDto;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import com.happiday.Happi_Day.jwt.JwtTokenDto;
import com.happiday.Happi_Day.jwt.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserAuthController {

    private final UserRepository userRepository;
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody UserRegisterDto dto) {
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .realname(dto.getRealname())
                .phone(dto.getPhone())
                .role(RoleType.USER)
                .build();
        manager.createUser(userDetails);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@Validated @RequestBody UserLoginDto dto) {
        UserDetails userDetails = manager.loadUserByUsername(dto.getUsername());
        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword()))
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCHED);
        JwtTokenDto token = new JwtTokenDto();
        token.setToken(jwtTokenUtils.generateToken(userDetails));
        LocalDateTime date = LocalDateTime.now();
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.setLastLoginAt(date);
        userRepository.save(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>("로그아웃되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@Validated @RequestBody UserRegisterDto dto) {
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .realname(dto.getRealname())
                .phone(dto.getPhone())
                .role(RoleType.ADMIN)
                .build();
        manager.createUser(userDetails);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}