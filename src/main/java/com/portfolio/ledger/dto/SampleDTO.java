package com.portfolio.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleDTO {
    private Long bno;
    private String text;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
