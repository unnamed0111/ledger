package com.portfolio.ledger.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
//@RequiredArgsConstructor // passwordEncoder 테스트 용도로 생성할려고 주석함
public class CustomUserDetailService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    // 테스트 용도
    public CustomUserDetailService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("..................USER DETAIL SERVICE..................");
        log.info("loadUserByUserName(ID) : " + username);

        UserDetails userDetails = User.builder()
                .username("user1")
//                .password("1111")
                .password(passwordEncoder.encode("1111"))
                .authorities("ROLE_USER")
                .build();

        log.info("..........USER DETAIL INFO : " + userDetails.toString());

        return userDetails;
    }
}
