package com.happiday.Happi_Day.jwt;

import com.happiday.Happi_Day.domain.entity.user.CustomUserDetails;
import com.happiday.Happi_Day.domain.service.JpaUserDetailsManager;
import com.happiday.Happi_Day.domain.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final JpaUserDetailsManager manager;
    private final StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.split(" ")[1]; // access Token 추출
            log.info("accessToken : " + accessToken);
            // accessToken이 유효하지 않다면
            if (!jwtTokenUtils.validateAccessToken(accessToken)) {
                log.info("재발급 해야 돼");
                String username = jwtTokenUtils.getUsername(accessToken);
                log.info("이메일은 " + username);
                String refreshToken = redisTemplate.opsForValue().get(username);
                log.info("refreshToken : " + refreshToken);
                if (refreshToken != null && jwtTokenUtils.validateRefreshToken(refreshToken)) {
                    UserDetails userDetails = manager.loadUserByUsername(username);
                    accessToken = jwtTokenUtils.createAccessToken((CustomUserDetails) userDetails);
                    log.info("accessToken 재발급 : " + accessToken);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired refresh token");
                    return;
                }
            }

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            String username = jwtTokenUtils.parseClaims(accessToken).getSubject();

            UserDetails userDetails = manager.loadUserByUsername(username);
            AbstractAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    accessToken,
                    userDetails.getAuthorities()
            );
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
    }
}
