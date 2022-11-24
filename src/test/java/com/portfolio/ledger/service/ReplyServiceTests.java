package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Reply;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.dto.ReplyDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
    @Autowired
    ReplyService replyService;

    private Long testAno = 95L;

    @BeforeEach
    public void registerTest() {
        log.info("..........................REGISTER REPLY..........................");

        ReplyDTO replyDTO = ReplyDTO.builder()
                .ano(testAno)
                .content("댓글 서비스 확인")
                .writer("댓글유저")
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
}
