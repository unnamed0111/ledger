package com.portfolio.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long        ano;

    @NotEmpty
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

    private boolean     snp;

    @NotEmpty
    private String      writer;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    // 뷰에서 총금액 보여주기
    public double getTotalPrice() {
        return price * amount;
    }
}
