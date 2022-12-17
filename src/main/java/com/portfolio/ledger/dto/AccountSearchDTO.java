package com.portfolio.ledger.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountSearchDTO {
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String title;
    private String content;
    private String writer;
    private Integer amountStart;
    private Integer amountEnd;
    private Double priceStart;
    private Double priceEnd;
}
