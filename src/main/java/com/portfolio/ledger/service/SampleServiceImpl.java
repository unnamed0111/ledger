package com.portfolio.ledger.service;

import com.portfolio.ledger.domain.Sample;
import com.portfolio.ledger.dto.PageRequestDTO;
import com.portfolio.ledger.dto.PageResponseDTO;
import com.portfolio.ledger.dto.SampleDTO;
import com.portfolio.ledger.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SampleServiceImpl implements SampleService {

    private final ModelMapper modelMapper;
    private final SampleRepository sampleRepository;

    @Override
    public Long register(SampleDTO sampleDTO) {
        Sample sample = modelMapper.map(sampleDTO, Sample.class);

        Long bno = sampleRepository.save(sample).getBno();

        return bno;
    }

    @Override
    public PageResponseDTO<SampleDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Sample> result = sampleRepository.searchAll(pageable);

        List<SampleDTO> dtoList = result.getContent().stream()
                .map(sample -> modelMapper.map(sample, SampleDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<SampleDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
