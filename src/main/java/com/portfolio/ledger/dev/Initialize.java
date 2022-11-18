package com.portfolio.ledger.dev;

import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.IntStream;

// 해당 클래스는 테스트 용도로 사용 (초기에 인메모리 DB에 샘플 데이터 생성 용도)
@Log4j2
public class Initialize implements ApplicationRunner {
    @Autowired
    private AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(".......................INITIALIZE SAMPLE DATA.......................");

        sampleInsert();
    }

    private void sampleInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            int     amount  = (int)(Math.random() * 100) + 1;
            double  price   = (Math.random() * 10000) + 100; // 100 원 이상

            AccountDTO accountDTO = AccountDTO.builder()
                    .date(LocalDate.now().minusDays(100 - i))
                    .title("Sample " + i)
                    .content("Sample content " + i)
                    .amount(amount)
                    .price(price)
                    .snp(Math.random() < 0.5)
                    .writer("user" + i % 10)
                    .build();

            accountService.register(accountDTO);
        });
    }
}
