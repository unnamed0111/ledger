package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Account;
import com.portfolio.ledger.domain.Reply;
import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.AccountSearchDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class AccountRepositoryTests {
    @Autowired
    private AccountRepository accountRepository;

    // 샘플 댓글 생성 용도
//    @Autowired
//    private ReplyRepository replyRepository;
//
//    @BeforeEach
//    public void testInitialize() {
//        insertReply();
//    }
//
//    // 댓글 생성 용도
//    public void insertReply() {
//        log.info("............................REPLY INSERT............................");
//
//        Account account = Account.builder().ano(95L).build();
//
//        IntStream.rangeClosed(1, 20).forEach(i -> {
//            Reply reply = Reply.builder()
//                    .account(account)
//                    .content("댓글...." + i)
//                    .writer("User" + (i % 4))
//                    .build();
//
//            replyRepository.save(reply);
//        });
//    }

//    @BeforeEach
    public void initialize() {
        testInsert();
    }

    @Test
    public void testInsert() {
        log.info("..........................INSERT..........................");
        IntStream.rangeClosed(1, 100).forEach(i -> {
            int amount = 2;
            double price = 10.05;
            double totalPrice = amount * price;

            Account account = Account.builder()
                    .date(LocalDate.now().minusDays(100-i))
                    .title("title" + i)
                    .content("content" + i)
                    .amount(amount)
                    .price(price)
                    .snp(i % 2 == 0 ? true : false)
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

        Page<AccountDTO> result = accountRepository.searchList(pageable, null);

        long count = result.getTotalElements();
        List<AccountDTO> accountList = result.getContent();

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

        account.change(
                LocalDate.now(),
                "changed title",
                "changed content",
                1,
                100,
                false,
                "changedUser");

        accountRepository.save(account);
    }

    @Test
    public void testDelete() {
        log.info("..........................DELETE LIST..........................");

        accountRepository.deleteById(89L);
    }

    @Test
    public void testSearchWithReplyCount() {
        log.info("..........................SEARCH WITH REPLY COUNT..........................");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("ano").descending());

        Page<AccountDTO> result = accountRepository.searchList(pageable, null);
        result.getContent().forEach(accountDTO -> {
            log.info(accountDTO);
        });
    }

    @Test
    public void testSearchTotalPrice() {
        log.info("..........................SEARCH TOTAL PRICE..........................");

        Map<String, Double> result = accountRepository.searchTotalPrice();

        DecimalFormat df = new DecimalFormat("#.###");

        log.info("total sales price = " + df.format(result.get("totalSalesPrice")));
        log.info("total purchase price = " + df.format(result.get("totalPurchasePrice")));
    }

    @Test
    public void testSearch() {
        log.info(".................SEARCH TEST.................");

        List<Account> list = accountRepository.findAll();
        list.forEach(account -> log.info(account));

        AccountSearchDTO searchDTO = AccountSearchDTO.builder()
                .amountStart(70)
                .dateStart(LocalDate.of(2022,10,1))
                .build();

        log.info(searchDTO);

        Pageable pageable = PageRequest.of(0, 50, Sort.by("date").descending());
        Page<AccountDTO> result = accountRepository.searchList(pageable, searchDTO);

        log.info(".....................RESULT.....................");
        result.getContent().forEach(accountDTO -> log.info(accountDTO));
    }
}
