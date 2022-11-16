package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Sample;
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
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class SampleRepositoryTests {
    @Autowired
    private SampleRepository sampleRepository;

    @BeforeEach
    public void testBefore() {
        testInsert();
        testUpdate();
    }

    @Test
    public void testInsert() {
        log.info("...................Insert Test...................");

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Sample sample = Sample.builder()
                    .title("title" + i)
                    .text("content" + i)
                    .build();

            Sample result = sampleRepository.save(sample);
            log.info("Sample : " + result.getBno());
        });
    }

    @Test
    public void testSelectOne() {
        log.info("...................Select Test...................");

        Long bno = 50L;

        Optional<Sample> result = sampleRepository.findById(bno);

        Sample sample = result.orElseThrow();

        log.info(sample);
    }

    @Test
    public void testUpdate() {
        log.info("...................Update Test...................");

        Long bno = 95L;

        Optional<Sample> result = sampleRepository.findById(bno);
        Sample sample = result.orElseThrow();

        sample.change("Update Completed", "Fixed Contents");

        sampleRepository.save(sample);
    }

    @Test
    public void testDelete() {
        log.info("...................Delete Test...................");

        Long bno = 75L;

        sampleRepository.deleteById(bno);
    }

    @Test
    public void testSearchFiltered() {
        String[] types = {"t"};
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Sample> result = sampleRepository.searchFiltered(types, "5", pageable);

        log.info("totalPage : " + result.getTotalPages());
        log.info("size : " + result.getSize());
        log.info("number : " + result.getNumber());
        log.info(result.hasPrevious() + " : " + result.hasNext());

        log.info("......................Select Filtered Sample......................");
        result.getContent().forEach(sample -> log.info(sample));
    }

    @Test
    public void testSearchAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Sample> result = sampleRepository.searchAll(pageable);

        log.info(result);

        List<Sample> list = result.getContent();
        list.forEach(sample -> log.info(sample));
    }
}
