package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void createSample() {
        testInserts();
    }
    @Test
    public void testInserts() {
        log.info("..........................INSERT SAMPLE..........................");

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .mid("user" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("user" + i + "@email" + i + ".com")
                    .build();

            member.addRole(MemberRole.USER);

            if(i >= 90) {
                member.addRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);
        });
    }

    @Test
    public void testSearchBySorted() {
        log.info("........................SEARCH MEMBER........................");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("regDate").descending());

        Page<Member> result = memberRepository.search(pageable);

        log.info("^^^^^^^^^^^^^^^^^^^^^TEST METHOD^^^^^^^^^^^^^^^^^^^^^");
        List<Member> list = result.getContent();
        list.forEach(member -> log.info(member));
    }

    @Test
    public void testSelectAll() {
        log.info("............................SELECT ALL............................");

        List<Member> list = memberRepository.findAll();
        list.forEach(member -> log.info(member));
    }

    @Test
    public void testRead() {
        log.info("............................READ WITH ROLES............................");

        Optional<Member> result = memberRepository.getWithRoles("user100");

        Member member = result.orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
    }
}
