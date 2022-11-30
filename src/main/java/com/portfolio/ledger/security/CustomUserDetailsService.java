package com.portfolio.ledger.security;

import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.repository.MemberRepository;
import com.portfolio.ledger.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("..................USER DETAIL SERVICE..................");
        log.info("loadUserByUserName(ID) : " + username);

        Optional<Member> result = memberRepository.getWithRoles(username);

        if(result.isEmpty()) {
            throw new UsernameNotFoundException("username not found");
        }

        Member member = result.get();

        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getMid(),
                member.getMpw(),
                member.getEmail(),
                member.isDel(),
                false,
                member.getRoleSet()
                        .stream()
                        .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                        .collect(Collectors.toList())
        );

//        UserDetails userDetails = User.builder()
//                .username("user1")
////                .password("1111")
//                .password(passwordEncoder.encode("1111"))
//                .authorities("ROLE_USER")
//                .build();

        log.info("..........MEMBER SECURITY DTO INFO : " + memberSecurityDTO.toString());

        return memberSecurityDTO;
    }
}
