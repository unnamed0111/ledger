package com.portfolio.ledger.service;

import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class AccountServiceTests {
    @Autowired
    private AccountService accountService;

//    @BeforeEach
    public void testInitialize() {
        // 기록 저장
        testRegister();
    }

    @Test
    public void testRegister() {
        log.info(".........................REGISTER.........................");

        IntStream.rangeClosed(1, 100).forEach(i -> {
            AccountDTO accountDTO = AccountDTO.builder()
                    .date(LocalDate.now().minusDays(100 - i))
                    .title("Title Sample" + i)
                    .content("Content Sample" + i)
                    .amount(i)
                    .price(100.0)
                    .snp(i % 2 == 0 ? true : false)
                    .writer("user" + i)
                    .build();

            accountService.register(accountDTO);
        });
    }

    @Test
    public void testGetOne() {
        log.info(".........................GET.........................");

        AccountDTO accountDTO = accountService.get(50L);
        log.info(accountDTO);
    }

    @Test
    public void testModify() throws Exception {
        log.info(".........................MODIFY.........................");

        Long ano = 97L;

        AccountDTO accountDTO = AccountDTO.builder()
                .date(LocalDate.now())
                .title("Fixed Title")
                .content("Fixed Content")
                .amount(250)
                .price(22.2)
                .snp(AccountDTO.SYMBOL_SALES)
                .writer("ModifiedUser")
                .build();

        accountDTO.setAno(ano);

        log.info("original dto : " + accountDTO);

        accountService.modify(accountDTO);
    }

    @Test
    public void testRemove() throws Exception {
        Long ano = 87L;

        accountService.remove(AccountDTO.builder().ano(ano).build());
    }

    @Test
    public void testAll() throws Exception {
        testModify();
        testRemove();
    }

    @Test
    public void testTotalPrice() {
        Map<String, Double> result = accountService.getTotalPrice();

        log.info("..................................GET TOTAL PRICE..................................");
        log.info(result);
    }
}
