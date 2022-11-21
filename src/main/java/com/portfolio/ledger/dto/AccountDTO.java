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
    static public final boolean SYMBOL_SALES    = true;
    static public final boolean SYMBOL_PURCHASE = false;

    private Long        ano;

    @NotNull(message = "{required.accountDTO.date}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate   date;

    @NotEmpty(message = "{required.accountDTO.title}")
    @Size(min = 1, max = 100)
    private String      title;

    @NotEmpty(message = "{required.accountDTO.content}")
    private String      content;

    @NotNull(message = "{required.accountDTO.amount}")
    @Positive
    private Integer         amount;

    @NotNull(message = "{required.accountDTO.price}")
    @Positive
    private Double      price;

    @Builder.Default
    private boolean     snp = SYMBOL_PURCHASE;

    @NotEmpty(message = "{required.accountDTO.writer}")
    private String      writer;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    // 뷰에서 총금액 보여주기
    public double getTotalPrice() {
        return price * amount;
    }
}
