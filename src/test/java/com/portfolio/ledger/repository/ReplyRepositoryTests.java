package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.domain.Reply;
import com.portfolio.ledger.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void testInsert() {
        Long ano = 95L;

        Account account = Account.builder().ano(ano).build();

        Reply reply = Reply.builder()
                .account(account)
                .content("댓글....")
                .writer("User1")
                .build();

        replyRepository.save(reply);
    }
}
