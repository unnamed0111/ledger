package com.portfolio.ledger.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private int amount = 1;

    @Column(nullable = false)
    @Builder.Default
    private double price = 0;

    @Column(nullable = false)
    private boolean snp; // 지출/매출 구분

    @Column(length = 50, nullable = false)
    private String writer;

    public void change(String title,
                       String content,
                       int amount,
                       double price,
                       boolean snp) {
        this.title      = title;
        this.content    = content;
        this.amount     = amount;
        this.price      = price;
        this.snp        = snp;
    }
}
