package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
    @Autowired private MemberRepository memberRepository;

    @Test
    public void testInsert() {
        log.info("===========================INSERT MEMBER===========================");

        Member member = Member.builder()
                .mid("user1")
                .mpw("1234")
                .email("aaaa@aaa.com")
                .social(false)
                .del(false)
                .build();
        member.addRole(MemberRole.USER);

        Member member2 = Member.builder()
                .mid("user1")
                .mpw("123456")
                .email("bbbb@aaa.com")
                .social(true)
                .del(false)
                .build();
        member.addRole(MemberRole.USER);

        memberRepository.save(member);
        memberRepository.save(member2);
    }
}
