package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.domain.Reply;
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

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    private Long ano = 95L;

    @Test
    public void testInsert() {
        log.info("............................REPLY INSERT............................");

        Account account = Account.builder().ano(ano).build();

        IntStream.rangeClosed(1, 20).forEach(i -> {
            Reply reply = Reply.builder()
                    .account(account)
                    .content("댓글...." + i)
                    .writer("User" + (i % 4))
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    public void testAccountReplies() {
        log.info("............................REPLY SELECT BY ACCOUNT............................");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfAccount(ano, pageable);

        result.getContent().forEach(reply -> {
            log.info(reply);
        });
    }
}
