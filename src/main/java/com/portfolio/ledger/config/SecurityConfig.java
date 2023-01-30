package com.portfolio.ledger.config;

import com.portfolio.ledger.security.CustomUserDetailsService;
import com.portfolio.ledger.security.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // Controller 전체 메소드에 대해서 접근권한 설정
public class SecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info(".......................SECURITY CONFIGURE.......................");

        //[ H2 데이터 베이스 관련 설정 배포시 삭제/주석 ===========================================================
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll();

        http.headers().frameOptions().disable();
        // H2 데이터 베이스 관련 설정 배포시 삭제/주석 ] ===========================================================

        http
                .formLogin() // 로그인을 폼방식으로 처리한다고 명시
                .loginPage("/member/login") // 로그인 사용자 경로를 설정
                .successHandler(loginSuccessHandler);

        http
                .logout()
                .logoutUrl("/member/logout");

        http.csrf().disable(); // CSRF 토큰 이용을 비활성화

        http
                .rememberMe() // 쿠키를 발행해서 로그인 기억 기능 설정 (로그인 페이지를 커스터마이징 했을 때 사용)
                .key("12345678") // 쿠키의 값을 인코딩하기 위한 키값
                .tokenRepository(persistentTokenRepository()) // 쿠키값과 사용자 정보 저장소 설정
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60 * 60 * 24 * 30);

        http
                .oauth2Login() // OAuth2 로그인 활성화
                .loginPage("/member/login") // OAuth2 로그인 페이지 설정
                .defaultSuccessUrl("/table/list");

        return http.build(); // 인증/접근 방식을 초기화 시키고 직접 설정할려면 build()를 사용해서 설정해야함
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("........................WEB CONFIGURE........................");

        // 정적 자원들을 스프링 시큐리티에서 제외시키기
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // 정보를 저장하기 위해서 추가
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
}
