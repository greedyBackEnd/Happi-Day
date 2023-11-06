package com.happiday.Happi_Day.config;

import com.happiday.Happi_Day.jwt.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessDeniedHandler ad, AuthenticationEntryPoint ap) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(e -> e.accessDeniedHandler(ad).authenticationEntryPoint(ap))
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authHttp -> authHttp
                        .requestMatchers(
                                HttpMethod.GET,
                                "/",
                                "/api/v1/chat",
                                "/api/v1/events/**"
                        ).permitAll()
                        .requestMatchers(
                                "/**",
                                "/error",
                                "/views/**",
                                "/static/**",
                                "/ws",
                                "/api/v1/auth/signup",
                                "/api/v1/auth/login"
                        )
                        .permitAll()
                        .anyRequest().authenticated()
                )
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("ACCESS DENIED");
            response.getWriter().flush();
            response.getWriter().close();
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("UNAUTHORIZED");
            response.getWriter().flush();
            response.getWriter().close();
        };
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(
                Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "PATCH", "OPTION"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
