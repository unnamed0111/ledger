package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.domain.MemberRole;
import com.portfolio.ledger.domain.Reply;
import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.service.AccountService;
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
import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    @Autowired private ReplyRepository replyRepository;
    @Autowired private AccountRepository accountRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    private Long ano = 1L;

    @BeforeEach
    public void insert() {
        insertAccount();
        testInsert();
    }
    private void insertMember() {
        log.info("=====================INSERT MEMBER=====================");

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .mid("user" + i)
                    .mpw(passwordEncoder.encode("0000"))
                    .email("user" + i + "@email" + i + ".com")
                    .build();

            member.addRole(MemberRole.USER);

            if(i >= 6) {
                member.addRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);
        });
    }
    private void insertAccount() {
        log.info("=====================INSERT ACCOUNT=====================");

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Account account = Account.builder()
                    .date(LocalDate.now().minusDays(i))
                    .title("지출 내역" + i)
                    .content("테스트 내역입니다 단가: " + (i * 10.0) + " , 수량: " + i)
                    .price(i * 10.0)
                    .amount(i)
                    .member(Member.builder().uid(1L).build())
                    .snp(i % 2 == 0 ? AccountDTO.SYMBOL_SALES : AccountDTO.SYMBOL_PURCHASE)
                    .build();

            accountRepository.save(account);
        });
    }

    @Test
    public void testInsert() {
        log.info("............................REPLY INSERT............................");

        Account account = Account.builder().ano(ano).build();
        Member member = Member.builder().uid(1L).build();

        Reply reply = Reply.builder()
                .account(account)
                .member(member)
                .content("테스트 댓글")
                .build();

        Reply result = replyRepository.save(reply);
        log.info(result + "Member ID : " + result.getMember().getUid() + "Account ano : " + result.getAccount().getAno());
    }

    @Test
    public void testAccountReplies() {
        log.info("............................REPLY SELECT BY ACCOUNT............................");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfAccount(ano, pageable);

        result.getContent().forEach(reply -> {
            log.info("writer : " + reply.getWriter() + " | " + reply);
        });
    }
}
