package com.portfolio.ledger.repository;

import com.portfolio.ledger.domain.Sample;
import com.portfolio.ledger.repository.search.SampleSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SampleRepository extends JpaRepository<Sample, Long>, SampleSearch {
    List<Sample> findByBnoBetween(Long start, Long end); // 반환타입이 Page<?> 이면 매개변수로 Pageable 객체를 받아야함

    @Query(value = "select s from Sample s  where s.text like concat('%', :keyword, '%') ", nativeQuery = true)
    Page<Sample> findKeyword(String keyword, Pageable pageable); // JPQL
}
