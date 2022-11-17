package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Account;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class AccountRepositoryTests {
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void testInitialize() {
        testInsert();
        testUpdate();
        testDelete();
    }

    @Test
    public void testInsert() {
        log.info("..........................INSERT..........................");
        IntStream.rangeClosed(1, 100).forEach(i -> {
            int amount = (int)Math.ceil(Math.random() * 20);
            double price = 1000 + Math.ceil(Math.random() * 100000);
            double totalPrice = amount * price;

            Account account = Account.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .amount(amount)
                    .price(price)
                    .snp(false)
                    .writer("user" + i)
                    .build();

            accountRepository.save(account);
        });
    }

    @Test
    public void testSearchList() {
        log.info("..........................SEARCH LIST..........................");

        int page = 2;

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("ano").descending());

        Page<Account> result = accountRepository.searchList(pageable);

        long count = result.getTotalElements();
        List<Account> accountList = result.getContent();

        log.info("..........................SEARCH RESULT..........................");
        log.info("Total Elements : " + count);
        log.info("Searched Elements :");

        accountList.forEach(account -> {
            double totalPrice = account.getPrice() * account.getAmount();
            log.info("totalPrice = " + totalPrice + " " + account);
        });
    }

    @Test
    public void testUpdate() {
        log.info("..........................UPDATE LIST..........................");

        Account account = accountRepository.findById(90L).orElseThrow();

        account.change("changed title",
                "changed content",
                1,
                100,
                false);

        accountRepository.save(account);
    }

    @Test
    public void testDelete() {
        log.info("..........................DELETE LIST..........................");

        accountRepository.deleteById(89L);
    }

}
