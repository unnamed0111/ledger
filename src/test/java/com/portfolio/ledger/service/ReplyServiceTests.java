package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Reply;
import com.portfolio.ledger.dto.AccountDTO;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.dto.ReplyDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
    @Autowired ReplyService replyService;
    @Autowired AccountService accountService;

    private Long testAno = 1L;

    @Test
    public void testAll() throws Exception {
        registerTest();
        modifyTest();
        getListOfAccountTest();
    }

    @Test
    public void registerTest() {
        log.info("......REGISTER ACCOUNT......");

        AccountDTO accountDTO = AccountDTO.builder()
                .date(LocalDate.now())
                .title("Title Sample" + 1)
                .content("Content Sample" + 1)
                .amount(1)
                .price(100.0)
                .snp(true)
                .uid(1L)
                .build();

        accountService.register(accountDTO);

        log.info("..........................REGISTER REPLY..........................");

        ReplyDTO replyDTO = ReplyDTO.builder()
                .ano(testAno)
                .content("댓글 서비스 확인")
                .uid(1L)
                .build();

        replyService.register(replyDTO);
    }

    @Test
    public void getListOfAccountTest() {
        log.info("..........................REPLY LIST OF ACCOUNT..........................");

        Long ano = testAno;
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<ReplyDTO> pageResponseDTO = replyService.getListOfAccount(ano, pageRequestDTO);

        log.info(pageResponseDTO);
    }

    @Test
    public void modifyTest() throws Exception {
        log.info("..........................MODIFY REPLY..........................");

        ReplyDTO modReply = ReplyDTO.builder()
                .rno(1L)
                .uid(1L)
                .content("수정 완료")
                .build();

        replyService.modify(modReply);
    }
}
