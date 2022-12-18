package com.portfolio.ledger.dev;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.domain.Member;
import com.portfolio.ledger.domain.MemberRole;
import com.portfolio.ledger.domain.Reply;
import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.repository.MemberRepository;
import com.portfolio.ledger.repository.ReplyRepository;
import com.portfolio.ledger.service.AccountService;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.stream.IntStream;

@Log4j2
@Component
public class Initialize implements ApplicationRunner {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createLoginTable();
        createUsers();
        createAccount();
        createAccounts();
        createReply();
    }
    private void createLoginTable() throws Exception {
        log.info("............CREATE TABLE PERSISTENT FOR LOGINS............");

        @Cleanup Connection connection = dataSource.getConnection();

        Statement statement = connection.createStatement();
        String sql =    "CREATE TABLE persistent_logins (" +
                            "username VARCHAR(64) NOT NULL ," +
                            "series VARCHAR(64) PRIMARY KEY ," +
                            "token VARCHAR(64) NOT NULL ," +
                            "last_used TIMESTAMP NOT NULL" +
                        ")";

        statement.execute(sql);
    }

    private void createUsers() {
        log.info("..........................INSERT SAMPLE..........................");

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

    private void createAccount() {
        log.info("..........................CREATE ACCOUNTS..........................");

        AccountDTO salesDTO = AccountDTO.builder()
                .date(LocalDate.now())
                .title("매출 내역 1")
                .content("테스트 매출 내역입니다. 단가 100원 수량 100개")
                .price(100.0)
                .amount(100)
                .writer("user1")
                .snp(AccountDTO.SYMBOL_SALES)
                .build();

        AccountDTO purchaseDTO = AccountDTO.builder()
                .date(LocalDate.now().minusDays(10))
                .title("지출 내역 1")
                .content("테스트 지출 내역입니다. 단가 322.22원 수량 40개")
                .price(322.22)
                .amount(40)
                .writer("user2")
                .snp(AccountDTO.SYMBOL_PURCHASE)
                .build();

        accountService.register(salesDTO);
        accountService.register(purchaseDTO);
    }

    private void createReply() {
        log.info(".............................CREATE REPLIES.............................");

        Account refAccount = Account.builder()
                .ano(1L)
                .build();

        IntStream.rangeClosed(1, 5).forEach(i -> {
            Reply reply = Reply.builder()
                    .account(refAccount)
                    .content("테스트 댓글..." + i)
                    .writer("user" + i)
                    .build();

            replyRepository.save(reply);
        });
    }

    private void createAccounts() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            AccountDTO accountDTO = AccountDTO.builder()
                    .date(LocalDate.now().minusDays(i))
                    .title("지출 내역" + i)
                    .content("테스트 내역입니다 단가: " + (i * 10.0) + " , 수량: " + i)
                    .price(i * 10.0)
                    .amount(i)
                    .writer("user2")
                    .snp(i % 2 == 0 ? AccountDTO.SYMBOL_SALES : AccountDTO.SYMBOL_PURCHASE)
                    .build();

            accountService.register(accountDTO);
        });
    }
}