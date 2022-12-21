package com.portfolio.ledger.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountSearchDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;
    private String title;
    private String content;
    private String writer;
    private Integer amountStart;
    private Integer amountEnd;
    private Double priceStart;
    private Double priceEnd;

    public String getLink() {
        StringBuilder result = new StringBuilder();

        if(dateStart != null) result.append("&dateStart=" + dateStart);
        if(dateEnd != null) result.append("&dateEnd=" + dateEnd);

        if(title != null && title.length() > 0) result.append("&title=" + title);
        if(content != null && content.length() > 0) result.append("&content=" + content);
        if(writer != null && writer.length() > 0) result.append("&writer=" + writer);

        if(amountStart != null && amountStart > 0) result.append("&amountStart=" + amountStart);
        if(amountEnd != null && amountEnd > 0) result.append("&amountEnd=" + amountEnd);

        if(priceStart != null && priceStart > 0) result.append("&priceStart=" + priceStart);
        if(priceEnd != null && priceEnd > 0) result.append("&priceEnd=" + priceEnd);

        return result.toString();
    }
}
