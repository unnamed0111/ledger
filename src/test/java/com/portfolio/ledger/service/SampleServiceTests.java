package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Sample;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.dto.SampleDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class SampleServiceTests {
    @Autowired
    private SampleService sampleService;

    @Test
    public void testService() {
        log.info(sampleService.getClass().getName());
    }

    @BeforeEach
    public void testRegister() {
        log.info(".............................INSERT.............................");
        IntStream.rangeClosed(1,30).forEach(i -> {
            SampleDTO sampleDTO = SampleDTO.builder()
                    .title("title" + i)
                    .text("content" + i)
                    .build();

            sampleService.register(sampleDTO);
        });
    }

    @Test
    public void testGetList() {
        log.info(".............................GET LIST.............................");

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(2)
                .size(10)
                .build();

        PageResponseDTO<SampleDTO> pageResponseDTO = sampleService.getList(pageRequestDTO);

        List<SampleDTO> dtoList = pageResponseDTO.getDtoList();
        dtoList.forEach(sampleDTO -> log.info(sampleDTO));
    }
}
