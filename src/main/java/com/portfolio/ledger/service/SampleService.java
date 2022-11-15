package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Sample;
import com.portfolio.ledger.dto.SampleDTO;

import java.util.List;
import java.util.Optional;

public interface SampleService {
    Long register(SampleDTO sampleDTO);
    List<SampleDTO> getList();
}
