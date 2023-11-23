package com.happiday.Happi_Day.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

//    @PostMapping("/issue")
//    public JwtTokenDto issueJwtToken(@RequestBody JwtRequestDto dto) {
//        UserDetails user = manager.loadUserByUsername(dto.getUsername());
//        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        JwtTokenDto token = new JwtTokenDto();
//        token.setToken(jwtTokenUtils.generateToken(user));
//        return token;
//    }
//
//    @PostMapping("/secured")
//    public String checkSecure() {
//        log.info(SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName()
//        );
//        return "success";
//    }


}
