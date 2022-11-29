package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Sample;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.dto.SampleDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SampleService {
    Long register(SampleDTO sampleDTO);
    PageResponseDTO<SampleDTO> getList(PageRequestDTO pageRequestDTO);
}
