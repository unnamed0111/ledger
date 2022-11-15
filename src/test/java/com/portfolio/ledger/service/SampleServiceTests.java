package com.portfolio.ledger.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class SampleServiceTests {
    @Autowired
    private SampleService sampleService;

    @Test
    public void testRegister() {
        log.info(sampleService.getClass().getName());
    }
}
