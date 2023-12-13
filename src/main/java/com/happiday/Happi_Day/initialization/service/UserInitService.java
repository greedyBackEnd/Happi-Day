package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.user.RoleType;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInitService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void initUsers() {
        List<User> users = List.of(
                // 어드민
                User.builder()
                        .username("admin@email.com")
                        .password(bCryptPasswordEncoder.encode("admin"))
                        .nickname("admin")
                        .realname("admin")
                        .phone("01011111111")
                        .role(RoleType.ADMIN)
                        .isActive(true)
                        .isTermsAgreed(true)
                        .termsAt(LocalDateTime.now())
                        .build(),
                // 유저 1 : qwer (article writer, seller)
                User.builder()
                        .username("qwer@email.com")
                        .password(bCryptPasswordEncoder.encode("qwer"))
                        .nickname("qwer")
                        .realname("qwer")
                        .phone("01022222222")
                        .role(RoleType.USER)
                        .isActive(true)
                        .isTermsAgreed(true)
                        .termsAt(LocalDateTime.now())
                        .build(),
                // 유저 2 : asdf (comment writer, buyer)
                User.builder()
                        .username("asdf@email.com")
                        .password(bCryptPasswordEncoder.encode("asdf"))
                        .nickname("asdf")
                        .realname("asdf")
                        .phone("01033333333")
                        .role(RoleType.USER)
                        .isActive(true)
                        .isTermsAgreed(true)
                        .termsAt(LocalDateTime.now())
                        .build()
        );

        users.forEach(user -> {
            try {
                if (!userRepository.existsByUsername(user.getUsername())) {
                    userRepository.save(user);
                }
            } catch (Exception e) {
                log.error("DB Seeder 사용자 저장 중 예외 발생 - 사용자명: {}", user.getUsername(), e);
                throw new CustomException(ErrorCode.DB_SEEDER_USER_SAVE_ERROR);
            }
        });
    }
}
