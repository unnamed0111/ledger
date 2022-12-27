package com.portfolio.ledger.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano;

    @Column(nullable = false)
    private LocalDate date;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_mid")
    private Member member;

    public void change(LocalDate date,
                       String title,
                       String content,
                       int amount,
                       double price,
                       boolean snp) {

        this.date       = date;
        this.title      = title;
        this.content    = content;
        this.amount     = amount;
        this.price      = price;
        this.snp        = snp;
    }

    public String getWriter() {
        return getMember().getMid();
    }

    public void setWriter(String writer) {
        this.member = Member.builder().mid(writer).build();
    }
}
