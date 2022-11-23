package com.portfolio.ledger.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_account_ano", columnList = "account_ano")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "account")
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private String content;
    private String writer;
}
