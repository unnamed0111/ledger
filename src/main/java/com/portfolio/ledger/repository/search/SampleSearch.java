package com.portfolio.ledger.repository.search;

import com.portfolio.ledger.domain.Sample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SampleSearch {
    Page<Sample> search1(Pageable pageable);
    Page<Sample> searchFiltered(String[] types, String keyword, Pageable pageable);
    Page<Sample> searchAll(Pageable pageable);
}
