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
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class AccountServiceTests {
    @Autowired
    private AccountService accountService;

    @BeforeEach
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
                    .price(100L)
                    .snp(i % 2 == 0 ? true : false)
                    .writer("user" + i)
                    .build();

            accountService.register(accountDTO);
        });
    }

    @Test
    public void testGetList() {
        log.info(".........................GET LIST.........................");

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(3)
                .size(20)
                .build();

         PageResponseDTO<AccountDTO> responseDTO = accountService.getList(pageRequestDTO);

         responseDTO.getDtoList().forEach(accountDTO -> log.info(accountDTO));
    }
}
