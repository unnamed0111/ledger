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
@ToString(exclude = {"account", "member"})
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Account_ano")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_mid", updatable = false)
    private Member member;

    private String content;

    public void changeContent(String content) {
        this.content = content;
    }
    public String getWriter() {
        return this.member.getMid();
    }

    public void setWriter(Long uid) {
        this.member = Member.builder().uid(uid).build();
    }
}
