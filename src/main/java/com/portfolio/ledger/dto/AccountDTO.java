package com.portfolio.ledger.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDTO {
    static final boolean SYMBOL_SALES    = true;
    static final boolean SYMBOL_PURCHASE = false;

    private Long        ano;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate   date;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String      title;

    @NotEmpty
    private String      content;

    @Positive
    private int         amount;

    @Positive
    private double      price;

    @Builder.Default
    private boolean     snp = SYMBOL_SALES;

    @NotEmpty
    private String      writer;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    // 뷰에서 총금액 보여주기
    public double getTotalPrice() {
        return price * amount;
    }
}
