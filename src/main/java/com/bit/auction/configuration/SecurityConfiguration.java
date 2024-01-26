package com.bit.auction.configuration;

import com.bit.auction.handler.LoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers("/auction/register").hasAnyRole("USER", "ADMIN");
                    authorizeRequests.anyRequest().permitAll();
                })
                // 로그인, 로그아웃 설정
                .formLogin((formLogin) -> {
                    formLogin.loginPage("/user/login");
                    formLogin.usernameParameter("userId");
                    formLogin.passwordParameter("userPw");
                    formLogin.loginProcessingUrl("/user/login");
                    formLogin.failureHandler(loginFailureHandler);
                    formLogin.defaultSuccessUrl("/");
                })

                // 로그아웃 처리
                .logout((logout) -> {
                    logout.logoutUrl("/user/logout");
                    logout.invalidateHttpSession(true);
                    logout.logoutSuccessUrl("/user/login");
                })
                .build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
