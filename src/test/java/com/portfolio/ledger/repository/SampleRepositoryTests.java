package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Sample;
import lombok.extern.log4j.Log4j2;
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

    @Test
    public void testAll() {
        testInsert();
        testSelectOne();
        testUpdate();
        testDelete();
//        testBetween();
//        testSearch1();
        testSearchFiltered();
    }

    @Test
    public void testInsert() {
        log.info("...................Insert Test...................");

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Sample sample = Sample.builder()
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

        Long bno = 50L;

        Optional<Sample> result = sampleRepository.findById(bno);
        Sample sample = result.orElseThrow();

        sample.change("Update Completed");

        sampleRepository.save(sample);
    }

    @Test
    public void testDelete() {
        log.info("...................Delete Test...................");

        Long bno = 75L;

        sampleRepository.deleteById(bno);
    }

    @Test
    public void testPaging() {
        testInsert(); // 초기화

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Sample> result = sampleRepository.findAll(pageable);
    }

    @Test
    public void testBetween() {
        List<Sample> sampleList = sampleRepository.findByBnoBetween(20L, 30L);

        log.info(sampleList);
    }

    @Test
    public void testSearch1() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        sampleRepository.search1(pageable);
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
        // insert
        log.info("............................INSERT............................");
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Sample sample = Sample.builder()
                    .text("Test " + i)
                    .build();

            sampleRepository.save(sample);
        });

        // select
        log.info("............................SELECT............................");
        List<Sample> list = sampleRepository.searchAll();
        list.forEach(sample -> log.info(sample));
    }
}
