package com.bit.auction.configuration;

import com.bit.auction.handler.LoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.amazonaws.services.ec2.model.PrincipalType.Role;

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
//                    authorizeRequests.anyRequest().permitAll();
                    // '/'요청은 모든 사용자가 접근 가능하도록 설정
                    authorizeRequests.requestMatchers("/").permitAll();
                    // css, js, image 등 정적 리소스들에 대한 요청도 모든 사용자가 접근 가능하도록 설정
                    authorizeRequests.requestMatchers("/css/**").permitAll();
                    authorizeRequests.requestMatchers("/js/**").permitAll();
                    authorizeRequests.requestMatchers("/img/**").permitAll();
                    // /user/로 시작하는 요청
                 //   authorizeRequests.requestMatchers("/user/**").permitAll();
//                    authorizeRequests.requestMatchers("/user/id-check").permitAll();
//                    authorizeRequests.requestMatchers("/user/join").permitAll();
//                    authorizeRequests.requestMatchers("/user/login").permitAll();
//                    authorizeRequests.requestMatchers("/user/join-view").permitAll();
//                    authorizeRequests.requestMatchers("/user/login-view").permitAll();
//                    authorizeRequests.requestMatchers("/user/inquiry-view").permitAll();
//                    authorizeRequests.requestMatchers("/user/inquiry").permitAll();


                    // 사용자 정보 변경인 '/user/change-user-info' 요청은 로그인된 사용자만 접근 가능하도록
//                    authorizeRequests.requestMatchers("/user/change-user-info").authenticated();
                    authorizeRequests.requestMatchers("/api/**").permitAll();
                    // 권한으로 접근제어
                    // 게시판의 모든 요청 'USER', 'ADMIN' 권한을 가진 사용자만 접근 가능하도록 설정
//                    authorizeRequests.requestMatchers("/board/**").hasAnyRole("USER", "ADMIN");
                    //authorizeRequests.requestMatchers("/user/inquiry-view").hasAnyRole("USER", "ADMIN");
                    // 관리자 페이지에 대한 접근제어
//                    authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN");
                    // 위에 설정한 요청외의 모든 요청은 인증된 사용자(로그인한 사용자)만 접근가능하도록 설정
                    authorizeRequests.requestMatchers("/auction/register").hasAnyRole("USER", "ADMIN");
                    authorizeRequests.requestMatchers("/inquiry/**").hasAnyRole("USER", "ADMIN");
                    authorizeRequests.requestMatchers("/mypage/**").hasRole("USER");
//                    authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN");
                 //   authorizeRequests.anyRequest().authenticated();
                    authorizeRequests.anyRequest().permitAll();

                })
                // 로그인, 로그아웃 설정
                .formLogin((formLogin) -> {
                    formLogin.loginPage("/user/login-view");
                    formLogin.usernameParameter("userId");
                    formLogin.passwordParameter("userPw");
                    formLogin.loginProcessingUrl("/user/loginProc");
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
